package hbase.temp;

import org.apache.hadoop.hbase.util.Bytes;

import news.News;
import news.NewsDao;

public class NewsGetOne {
	public static void main(String[] args) {
		NewsDao newsDao = new NewsDao();
		byte[] rowkey = Bytes.toBytes("5814671301654581C9AF84F2891C7C3");
		News news = newsDao.get(rowkey);
		System.out.println(news.getTextSrc());
	}
}
