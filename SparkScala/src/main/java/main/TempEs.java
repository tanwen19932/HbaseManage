package main;

import com.mininglamp.nlp.languageDetect.LanguageUtil;
import controller.Constants;
import edu.buaa.nlp.entity.news.DicMap;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.es.client.ESClient;
import edu.buaa.nlp.es.constant.Configuration;
import edu.buaa.nlp.tw.common.DateUtil;
import edu.buaa.nlp.tw.common.HttpUtil2;
import es.ESBulkProcessorFactory;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static edu.buaa.nlp.tw.common.StringUtil.isNull;

public class TempEs {
	static Logger LOG = LoggerFactory.getLogger(TempEs.class);
	static List<String> allowLanguage = new ArrayList<>();
	static Client esRecent = null;
	static Client esTotal = null;
	static BulkProcessor esRecentBulk = null;
	static BulkProcessor esTotalBulk = null;
	static {
		allowLanguage.add("en");
		allowLanguage.add("zh");
		try {
			esRecent = ESClient.getClient(Configuration.CLUSTER_NAME, Configuration.INDEX_SERVER_ADDRESS);
			esTotal = ESClient.getClient(Configuration.TOTAL_CLUSTER_NAME, Configuration.TOTAL_INDEX_SERVER_ADDRESS);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		esRecentBulk = ESBulkProcessorFactory.buildBulkProcessor(esRecent);
		esTotalBulk = ESBulkProcessorFactory.buildBulkProcessor(esTotal);
	}

	static boolean checkLan(String lanCode) {
		for (String lan : allowLanguage) {
			if (lanCode.equals(lan)) {
				return true;
			}
		}
		return false;
	}

	boolean checkLan(ProcessedNews news) {
		for (String lan : allowLanguage) {
			if (news.getLanguageCode().equals(lan)) {
				return true;
			}
		}
		news.setLanguageCode("other");
		return false;
	}

	static String getLanByInet(String detectString) {
		JSONObject lanObj;
		detectString = URLEncoder.encode(detectString);
		try {
			lanObj = new JSONObject(HttpUtil2.getHttpContent("http://192.168.52.3:4202/detect?q=" + detectString));
			return lanObj.getJSONObject("responseData").getString("language");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "";
	}

	static String getLanByMing(String detectString) {
		return LanguageUtil.getInstance().detect(detectString);
	}

	static void fixLan(SearchHit hit) throws JSONException {
		JSONObject jsonObject = new JSONObject(hit.getSourceAsString());
		String uuid = jsonObject.getString("uuid");
		String detectLang = getLanByMing(jsonObject.getString("titleSrc") + "\n" + jsonObject.getString("abstractZh"));
		// String detectLang =
		// lanObj.getJSONObject("responseData").getString("language");
		// String detectLang = LanguageUtil
		// .langDetect(jsonObject.getString("titleSrc") + "\n" +
		// jsonObject.getString("abstractZh"));
		// System.out.println( "检测语言:"+ detectLang +
		// aNews.getTitleSrc() + aNews.getTextSrc() + " ------>>");
		if (!isNull(detectLang) && !(detectLang.equalsIgnoreCase(jsonObject.getString("languageCode")))) {
			LOG.info(" index:{} 修改uuid：{} 检测语言 {}-{} 标注语言 {}", hit.getIndex(), uuid,
					detectLang,DicMap.getLanguageZh(detectLang),
					jsonObject.getString("languageCode"));
			jsonObject.put("languageCode", detectLang);
			jsonObject.put("languageTname", DicMap.getLanguageZh(detectLang));
			IndexRequest request = new IndexRequest(hit.getIndex(), hit.getType(), uuid).source(jsonObject.toString());
			esRecentBulk.add(request);
			esTotalBulk.add(request);
		}
	}

	static void fixId(SearchHit hit) throws JSONException {
		JSONObject jsonObject = new JSONObject(hit.getSourceAsString());
		String uuid = jsonObject.getString("uuid");
		if (!uuid.equals(hit.getId())) {
			LOG.info(" index:{} 检测不等 删除 ES_ID：{}  UUID:{}  ", hit.getIndex(), hit.getId(), uuid);
			IndexRequest indexRequest = new IndexRequest(hit.getIndex(), hit.getType(), uuid)
					.source(jsonObject.toString());
			esRecentBulk.add(indexRequest);
			esTotalBulk.add(indexRequest);
			DeleteRequest delRequest = new DeleteRequest(hit.getIndex(), hit.getType(), hit.getId());
			esRecentBulk.add(delRequest);
			esTotalBulk.add(delRequest);
		}
	}

	static void fixLan(long startTime, long endTime) throws JSONException, MalformedURLException {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		QueryBuilder rangeQuery = QueryBuilders.rangeQuery("pubTime").from(startTime).to(endTime).includeLower(true)
				.includeUpper(false);
		boolQuery.must(rangeQuery);
		SearchRequestBuilder request = esRecent.prepareSearch("news2016*").setTypes("article")
				.addSort("uuid", SortOrder.ASC).setScroll(new TimeValue(600000)).setQuery(boolQuery).setSize(10000);
//		System.out.println(request);
		SearchResponse scrollResp = request.execute().actionGet(); // 10000 hits
		int totalHits = 0;
		while (true) {
			SearchHits hits = scrollResp.getHits();
			LOG.info("startTime :{} endTime : {} hit个数：{}/{} 耗时{}ms", DateUtil.getFixDateStr(startTime),
					DateUtil.getFixDateStr(endTime), totalHits+hits.getHits().length, hits.getTotalHits(),
					scrollResp.getTookInMillis());
			for (SearchHit hit : hits.getHits()) {
				totalHits++;
				fixId(hit);
				fixLan(hit);
			}
			scrollResp = esRecent.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000))
					.execute().actionGet();
			// Break condition: No hits are returned
			if (scrollResp.getHits().getHits().length == 0) {
				LOG.info("完毕！ startTime :{} endTime : {} hit个数：{}/{} 耗时{}ms 完毕", DateUtil.getFixDateStr(startTime),
						DateUtil.getFixDateStr(endTime),totalHits, hits.getTotalHits(),
						scrollResp.getTookInMillis());
				break;
			}
		}

	}

	static class esTask implements Runnable {

		BlockingQueue<Long> task;

		public esTask(BlockingQueue<Long> task) {
			this.task = task;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Long beginTime = task.poll();
					if (beginTime == null) {
						Thread.sleep(3000);
						continue;
					}
					long endTime = beginTime + 10000;
					fixLan(beginTime, endTime);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws ParseException, InterruptedException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Long beginTime = simpleDateFormat.parse(Constants.BEGIN_DATE_TIME).getTime();
		Long lastTime = beginTime;
		Long nextTime = lastTime - 10000;
		Long endTime = simpleDateFormat.parse(Constants.END_DATE_TIME).getTime();
		int threadNum = 10;
		ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
		BlockingQueue<Long> task = new LinkedBlockingQueue<>();
		for (int i = 0; i < threadNum; i++) {
			pool.execute(new esTask(task));
		}
		while (true) {
			long now = System.currentTimeMillis();
			if (Constants.isDeleteHash.equals("0")) {
				while (now - beginTime >= 10000) {
					task.add(beginTime);
					beginTime = beginTime + 10000;
					while (task.size() >= 50) {
						Thread.sleep(3000);
					}
				}
			}
			if (nextTime >= endTime) {
				task.add(nextTime);
				lastTime = nextTime;
				nextTime = lastTime - 10000;
				while (task.size() >= 50) {
					Thread.sleep(3000);
				}
			}

		}
	}
}
