package news;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import hbase.util.RowkeyUtil;

public class NewsUtil {
	protected static String INFO_COLS = "I";
	
	public static List<Put> genPuts(List<News> newsList){
		List<Put> putList = new LinkedList<>();
		for(News news : newsList){
			putList.add(genPut(news));
		}
		return putList;
	}
	
	public static Put genPut( News news){
		byte[] rowkey;
		if (news.getId() == null) {
			String a = RowkeyUtil.rowkey(news.getTitleSrc());
			rowkey = Bytes.toBytes(a);
		} else {
			rowkey = Bytes.toBytes(news.getId());
		}
		Put put = new Put(rowkey);

		addPut(put, INFO_COLS, NewsMap.MEDIA_TYPE, String.valueOf(news.getMediaType()));
		addPut(put, INFO_COLS, NewsMap.MEDIA_T_NAME, news.getMediaTname());

		addPut(put, INFO_COLS, NewsMap.TITLE_SRC, news.getTitleSrc());
		addPut(put, INFO_COLS, NewsMap.PUBDATE, String.valueOf(news.getPubdate()));
		addPut(put, INFO_COLS, NewsMap.TEXT_SRC, news.getTextSrc());

		addPut(put, INFO_COLS, NewsMap.WEBSITE_ID, news.getWebsiteId());
		addPut(put, INFO_COLS, NewsMap.MEDIA_NAME_SRC, news.getMediaNameSrc());
		addPut(put, INFO_COLS, NewsMap.MEDIA_NAME_ZH, news.getMediaNameZh());
		addPut(put, INFO_COLS, NewsMap.MEDIA_NAME_EN, news.getMediaNameEn());
		addPut(put, INFO_COLS, NewsMap.MEDIA_LEVEL, String.valueOf(news.getMediaLevel()));

		addPut(put, INFO_COLS, NewsMap.COUNTRY_NAME_ZH, news.getCountryNameZh());
		addPut(put, INFO_COLS, NewsMap.COUNTRY_NAME_EN, news.getCountryNameEn());
		addPut(put, INFO_COLS, NewsMap.PROVINCE_NAME_ZH, news.getProvinceNameZh());
		addPut(put, INFO_COLS, NewsMap.PROVINCE_NAME_EN, news.getProvinceNameEn());
		addPut(put, INFO_COLS, NewsMap.DISTRICT_NAME_ZH, news.getDistrictNameZh());
		addPut(put, INFO_COLS, NewsMap.DISTRICT_NAME_EN, news.getDistrictNameEn());

		addPut(put, INFO_COLS, NewsMap.LANGUAGECODE, news.getLanguageCode());
		addPut(put, INFO_COLS, NewsMap.LANGUAGE_T_NAME, news.getLanguageTname());

		addPut(put, INFO_COLS, NewsMap.AUTHOR, news.getAuthor());
		addPut(put, INFO_COLS, NewsMap.CREATED, String.valueOf(news.getCreated()));
		addPut(put, INFO_COLS, NewsMap.UPDATED, String.valueOf(news.getUpdated()));
		addPut(put, INFO_COLS, NewsMap.IS_ORIGINAL, String.valueOf(news.isOriginal()));
		addPut(put, INFO_COLS, NewsMap.VIEW, String.valueOf(news.getView()));
		addPut(put, INFO_COLS, NewsMap.URL, news.getUrl());
		addPut(put, INFO_COLS, NewsMap.DOC_LENGTH, String.valueOf(news.getDocLength()));

		addPut(put, INFO_COLS, NewsMap.TRANS_FROM_M, news.getTransFromM());
		addPut(put, INFO_COLS, NewsMap.PV, String.valueOf(news.getPv()));
		addPut(put, INFO_COLS, NewsMap.IS_HOME, String.valueOf(news.isHome()));
		addPut(put, INFO_COLS, NewsMap.IS_PICTURE, String.valueOf(news.isPicture()));

		addPut(put, INFO_COLS, NewsMap.COME_FROM, String.valueOf(news.getComeFrom()));

		addPut(put, INFO_COLS, NewsMap.COME_FROM_DB, String.valueOf(news.getComeFromDb()));
		addPut(put, INFO_COLS, NewsMap.USER_TAG, String.valueOf(news.getUserTag()));
//		put.setWriteToWAL(false);
		put.setDurability(Durability.SKIP_WAL);
		return put;
	}
	
	public static void addPut(Put put, String family, String quaifier, String value) {
		if (value != null) {
			put.add(Bytes.toBytes(family), Bytes.toBytes(quaifier), Bytes.toBytes(value));
		}
	}

}
