package handler.news;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import controller.Constants;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.entity.util.HbaseUtil;
import edu.buaa.nlp.es.client.ESClient;
import edu.buaa.nlp.es.constant.Configuration;
import edu.buaa.nlp.es.exception.ExceptionUtil;
import edu.buaa.nlp.util.DupConstants;
import es.ESBulkProcessorFactory;
import handler.Handler;
import net.sf.json.JSONObject;
import news.NewsUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 入库处理 1.分词入库 2.译文入库 3.类别统计 4.最终新闻入库
 * 
 * @author Vincent
 *
 */

public class SaveHandlerTW implements Handler<ProcessedNews> {
	private static Logger logger = Logger.getLogger(SaveHandlerTW.class);;
	static BlockingQueue<ProcessedNews> listSet = new LinkedBlockingQueue<ProcessedNews>(Constants.CONTAINER_CORE_SIZE);
	static int insertBulkCount = DupConstants.HBASE_PUT_BUFFER_SIZE * 10;
	static Client esRecent = null;
	static Client esTotal = null;
	static BulkProcessor esRecentBulk = null;
	static BulkProcessor esTotalBulk = null;
	static TableName tableOAName = TableName.valueOf("NewsArticleOA2");
	final static String INFO_COLS_OA = "O";
	static TableName tableTTName = TableName.valueOf("NewsArticleTT2");
	final static String INFO_COLS_TT = "T";
	static TableName tableBERName = TableName.valueOf("NewsArticleBER");
	final static String INFO_COLS_BER = "I";
	static TableName tableBE2Name = TableName.valueOf("NewsArticleBE2");
	final static String INFO_COLS_BE2 = "I";
	public static Connection conn;
	static Connection conn15;
	static Connection connH50;
	static BlockingQueue<ProcessedNews> container;

		
	static {
		buildES();
		buildHbase();
		ExecutorService pool  = Executors.newFixedThreadPool(3 ,new ThreadFactoryBuilder()
				.setNameFormat(SaveHandlerTW.class.getSimpleName()).build());
		for (int i = 0; i < 3; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					int retry = 0;
					while (true) {
						try {
							if (listSet.size() > insertBulkCount ) {
								logger.info("达到预计 insert Bulk 准备入库  insertBuikCount： "  + listSet.size() + "/"+ insertBulkCount);
								try {
									handle(listSet);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								if(retry >= 3 && listSet.size()!=0){
									retry =0 ;
									logger.info("未达到达到预计 insert Bulk并重试"+retry+"次 入库  insertBuikCount： " + listSet.size() + "/"+ insertBulkCount+ " 重试次数："+retry);
									handle(listSet);
								}
								retry++;
								logger.info("未达到达到预计 insert Bulk 等待3s  insertBuikCount： " + listSet.size() + "/"+ insertBulkCount+ " 重试次数："+retry);
								Thread.sleep(3000);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}catch(Error e){
							logger.error("致命错误： 线程死掉!!",e );
							System.exit(-1);
						}
					}
				}
			});
		}
	}
	public static void buildES() {
		try {
			esRecent = ESClient.getClient(Configuration.CLUSTER_NAME, Configuration.INDEX_SERVER_ADDRESS);
			esTotal = ESClient.getClient(Configuration.TOTAL_CLUSTER_NAME, Configuration.TOTAL_INDEX_SERVER_ADDRESS);
			esRecentBulk = ESBulkProcessorFactory.buildBulkProcessor(esRecent);
			esTotalBulk = ESBulkProcessorFactory.buildBulkProcessor(esTotal);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}
	public static void buildHbase() {
		try {
//			conn15 = ConnectionFactory.createConnection(HbaseUtil.getH15Conf());
//	        connH50 = ConnectionFactory.createConnection(HbaseUtil.getH50Conf());
	        conn= ConnectionFactory.createConnection(HbaseUtil.getConf());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static boolean insertES(List<ProcessedNews> list) {
		for (int i = 0; i < list.size(); i++) {
			ProcessedNews n = list.get(i);
			JSONObject obj = NewsUtil.getJson(n);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String indexName = "news"
					+ sdf.format(new Date(obj.getLong(edu.buaa.nlp.es.news.Mapper.FieldArticle.PUBDATE_SORT)));
			if(indexName.compareTo("news201001")<0){
				indexName = "news201001";
			}
			IndexRequest request = new IndexRequest( indexName,"article",n.getId()).source(obj);
			if (indexName.compareTo("news201601") >= 0 && indexName.compareTo("news201612") <= 0) {
				esRecentBulk.add(request);
			}
			esTotalBulk.add(request);
		}
		return true;
	}

	private static boolean insertHbase(List<ProcessedNews> news) {
		List<Put> putsOA = new ArrayList<>();
		List<Put> putsTT = new ArrayList<>();
		for (int i = 0; i < news.size(); i++) {
			ProcessedNews n = news.get(i);
//			FileUtils.fileAppendJson("INSERTJSON", new org.json.JSONObject(JSONObject.fromObject(n).toString()));
			putsOA.add( NewsUtil.getPutOA(n) );
			putsTT.add( NewsUtil.getPutTT(n) );
		}
		long s = System.currentTimeMillis();
		logger.info("Hbase    入库：" + news.size());
		try {
			Table tableOA = conn.getTable(tableOAName);
			Table tableTT = conn.getTable(tableTTName);
			tableOA.put(putsOA);
			tableTT.put(putsTT);
			tableOA.close();
			tableTT.close();
			logger.info("Hbase入库完毕：" + news.size() + " 耗时：" + (System.currentTimeMillis() - s) / 1000 + "秒");
		} catch (Exception e) {
			logger.error("Hbase入库失败：" + news.size() + " 耗时：" + (System.currentTimeMillis() - s) / 1000 + "秒");
			logger.error( ExceptionUtil.getExceptionTrace(e));
			return false;
		}
		return true;
	}


	public static boolean handle(BlockingQueue<ProcessedNews> news) throws Exception {
		List<ProcessedNews> newsList = new ArrayList<>();
		for (int i = 0; i < insertBulkCount; i++) {
			ProcessedNews tempNews = news.poll();
			if(tempNews==null) break;
			newsList.add(tempNews);
		}
		boolean result = false;
		try {
			result = insertHbase(newsList);
			result =  insertES(newsList)&&result;
		} catch (Throwable e) {
			logger.error("入库失败：++++++++++内容还至队列" + newsList.size());
			logger.error(ExceptionUtil.getExceptionTrace(e));
			for (ProcessedNews temp : newsList) {
				news.offer(temp);
			}
			result = false;
		}
		newsList = null;
		return result;
	}
	
	@Override
	public boolean handle(ProcessedNews news) {
		try {
			listSet.put(news);
		} catch (InterruptedException e) {
			logger.error(ExceptionUtil.getExceptionTrace(e));
			return false;
		}
		return true;
	}
}
