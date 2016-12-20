package handler.news;

import controller.Constants;
import edu.buaa.nlp.duplicate.HbaseNewsDupDetectSimple;
import edu.buaa.nlp.duplicate.IDupDetector;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.util.HashAlgorithms;
import edu.buaa.nlp.utils.texthash.TextHash;
import handler.Handler;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static handler.news.SaveHandlerTW.*;
/***
 * 比较耗CPU
 * 
 * @author Vincent
 * 
 */
public class DuplicateHandlerTW
		implements Handler<ProcessedNews>,Serializable {

	private static Logger LOG = LoggerFactory.getLogger(DuplicateHandlerTW.class);
	private static IDupDetector<ProcessedNews> duplicateDetector = HbaseNewsDupDetectSimple.getInstance();
	private static List<Delete> deletes = new ArrayList<>();
	@Override
	public boolean handle(ProcessedNews news) {
		if(news==null) return false;
		if(  Constants.isDeleteHash.equals("1")){
			try {
//				LOG.info("检测重复准备删除新闻Hash:{}",news.getId());
				deleteDupHash(news);
				return false;
			} catch (IOException e) {
				LOG.info("删除新闻Hash:{}失败",news.getId());
				e.printStackTrace();
			}
		}
		String dupType = duplicateDetector.isDup(news);
		if(dupType.length()>1)
			news.setSimilarityId(dupType);
//		if(dupType.equals("1")){
//			try {
//				LOG.debug("检测重复准备删除新闻{}",news.getId());
//			     deleteHbase(news);
//			     deleteES(news);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		return !dupType.equals("1");
	}
	
	void deleteDupHash(ProcessedNews news) throws IOException{
		TableName tableName = TableName.valueOf(Constants.HBASE_DUP_TABLE + news.getPubdate().substring(2, 7));
		Table table = conn.getTable(tableName);
		long mediaHashcode = HashAlgorithms.mixHash(news.getMediaNameSrc());
		long titleHashcode = HashAlgorithms.mixHash(news.getTitleSrc());
		long contentSimHash = TextHash.computeSimHashForString(news.getTextSrc());
		String key = String.valueOf(titleHashcode) + "|" + String.valueOf(contentSimHash) + "|"
				+ String.valueOf(mediaHashcode);
		Delete del = new Delete(Bytes.toBytes(key));
		table.delete(del);
	}
	
	void deleteHbase(ProcessedNews news) throws IOException{
		Table tableOA = conn.getTable(tableOAName);
		Table tableTT = conn.getTable(tableTTName);
		tableOA.delete(new Delete(Bytes.toBytes(news.getId())));
		tableTT.delete(new Delete(Bytes.toBytes(news.getId())));
		LOG.info("删除Hbase成功 ！{}",news.getId());
		
	}
	void deleteES(ProcessedNews news) {
		
		String indexName = "news"+news.getPubdate().substring(0,7).replace("-", "");
		if(indexName.compareTo("news201001")<0){
			indexName = "news201001";
		}
		String type = "article";
		if(indexName.compareTo("news201601")>0){
			esRecent.prepareDelete(indexName, type, news.getId())
				.get();
		}
		esTotal.prepareDelete(indexName, type, news.getId())
				.get();
		LOG.info("删除ES {} {}  成功 ！{}",indexName,type,news.getId());
	}
}
