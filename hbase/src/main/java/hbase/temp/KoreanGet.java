package hbase.temp;

import java.lang.reflect.Field;

import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.*;
import org.json.JSONObject;

import news.News;
import news.NewsDao;
import tw.utils.FileUtils;
import tw.utils.ReflectUtil;

public class KoreanGet {
	public static void main(String[] args) {
		NewsDao newsDao = new NewsDao(){
			@Override
			public void handleNews(News news) {
				// TODO Auto-generated method stub
				super.handleNews(news);
//				XStream xStream = new XStream();
//				xStream.processAnnotations(News.class);
//				String xml = xStream.toXML(news);
//				System.err.println(xml);
				if(news.getLanguageTname() == null) return;
				Field[] fileds = news.getClass().getDeclaredFields();
				JSONObject jo = new JSONObject();
				
				for(int i = 0 ; i < fileds.length ; i++ ){
					jo.put( fileds[i].getName(), ReflectUtil.getObjFValue(news, fileds[i]));
				}
				System.out.println( jo.toString() );
				FileUtils.fileAppendJson("KoreanJson.txt", jo);
			}
		};
		Filter filter1 = new SingleColumnValueFilter(
				Bytes.toBytes("I"),
				Bytes.toBytes("comeFrom"), CompareOp.EQUAL, Bytes.toBytes("Cision"));
//		ColumnPaginationFilter filter = new ColumnPaginationFilter(5, 0);
		FilterList filterList = new FilterList(
				FilterList.Operator.MUST_PASS_ALL);
		filterList.addFilter(filter1);
//		filterList.addFilter(filter);
		newsDao.handleAll(filterList);
	}
}	
