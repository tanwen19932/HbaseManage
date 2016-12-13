package news;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import edu.buaa.nlp.entity.news.MapperProcessedNews;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.util.DateUtil;
import net.sf.json.JSONObject;

public class NewsUtil {
	private static byte[] getBytes(String value){
		if(value==null) return null;
		return Bytes.toBytes(value);
	}
	private static byte[] getBytes(int value){
		return Bytes.toBytes(value);
	}
	private static byte[] getBytes(boolean value){
		return Bytes.toBytes(value);
	}
	private static String[] str2Arr(String str) {
		if (str == null || "".equals(str))
			return new String[] {};
		String[] arr = str.split("[;；]");
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}
	public static JSONObject getJson(ProcessedNews n){
		JSONObject obj = new JSONObject();
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.ID, n.getId());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.MEDIA_TNAME, n.getMediaTname());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.MEDIA_TYPE, n.getMediaType());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.WEBSITE_ID, n.getWebsiteId());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.MEDIA_NAME_SRC, n.getMediaNameSrc());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.MEDIA_NAME_ZH, n.getMediaNameZh());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.MEDIA_NAME_EN, n.getMediaNameEn());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.COUNTRY_NAME_EN, n.getCountryNameEn());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.COUNTRY_NAME_ZH, n.getCountryNameZh());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.PROVINCE_NAME_ZH, n.getProvinceNameZh());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.PROVINCE_NAME_EN, n.getProvinceNameEn());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.DISTRICT_NAME_ZH, n.getDistrictNameZh());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.DISTRICT_NAME_EN, n.getDistrictNameEn());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.MEDIA_LEVEL, n.getMediaLevel());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.AUTHOR, n.getAuthor());
		// TODO 时间现在输入时long类型，是否需要转换成如下类型
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.CREATE_TIME, n.getCreated());// DateUtil.getForDate("yyyy-MM-dd
																						// HH:mm:ss",
																						// new
																						// Timestamp(n.getCreated()*1000)));
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.UPDATE_TIME, n.getUpdated());// DateUtil.getForDate("yyyy-MM-dd
																						// HH:mm:ss",
																						// new
																						// Timestamp(n.getUpdated()*1000)));
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.PUBDATE, n.getPubdate()); // DateUtil.getForDate("yyyy-MM-dd
																					// HH:mm:ss",
																					// new
																					// Timestamp(n.getPubdate()*1000)));
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.PUBDATE_SORT,
				DateUtil.getDate("yyyy-MM-dd HH:mm:ss", n.getPubdate()).getTime());// 用于排序
		// obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.SNATCH_TIME,
		// DateUtil.getForDate("yyyy-MM-dd HH:mm:ss", new
		// Timestamp(n.getSnatchTime()*1000)));

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.LANGUAGE_CODE, n.getLanguageCode());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.LANGUAGE_TNAME, n.getLanguageTname());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.IS_ORIGINAL, n.isOriginal() ? "1" : "0");
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.VIEW, n.getView());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.URL, n.getUrl());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.DOC_LENGTH, n.getDocLength());// TODO
																						// 文本字节数长度

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TRANSFROMM, n.getTransFromM());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.PV, n.getPv());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.ISHOME, n.isHome() ? "1" : "0");
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.ISPICTURE, n.isPicture() ? " 1" : "0");

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.COME_FROM, n.getComeFrom());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.COME_FROM_DB, n.getComeFromDb());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.USER_TAG, n.getUserTag());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TRANSFER, n.getTransfer());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.SIMILARITY_ID, n.getSimilarityId());
		// TODO products和companies是否需要也变成Array
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.PRODUCTS, str2Arr(n.getProducts()));
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.COMPANIES, str2Arr(n.getCompanies()));

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.SENTIMENT_ID, n.getSentimentId());
		// obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.SENTIMENT_NAME,
		// n.getSentimentName());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.CATEGORY_ID, n.getCategoryId());
		// obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.CATEGORY_NAME,
		// n.getCategoryName());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.KEYWORDS_EN, str2Arr(n.getEnKeywords()));
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.KEYWORDS_ZH, str2Arr(n.getZhKeywords()));
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.ABSTRACT_EN, n.getEnSummary());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.ABSTRACT_ZH, n.getZhSummary());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TITLE_ZH, n.getZhTitle());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TITLE_SRC, n.getTitleSrc());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TITLE_EN, n.getEnTitle());

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TEXT_SRC, n.getTextSrc());

		if (n.getLanguageCode().equalsIgnoreCase("en") == false) // 如果是英文，则无需存储
		{
			obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TEXT_EN, n.getEnTxt());
		}
		if (n.getLanguageCode().equalsIgnoreCase("zh") == false) // 如果是中文，则无需存储
		{
			obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.TEXT_ZH, n.getZhTxt());
		}

		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.IS_SENSITIVE, n.getSensitive());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.SENSITIVE_TYPE, n.getSensitiveType());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.SENSITIVE_CLS, n.getSensitiveCls());
		obj.put(edu.buaa.nlp.es.news.Mapper.FieldArticle.DEL_FLAG, n.getDelFlag());
		return obj;
	}
	
	public static Put getPutTT(ProcessedNews n){
		Put putTT = new Put( getBytes( n.getId() ));
		putTT.addColumn( getBytes("T")
				, getBytes(MapperProcessedNews.NewsArticleTT.TITLESRC)
				, getBytes( n.getTitleSrc()) 
				);
		putTT.addColumn( getBytes("T")
				, getBytes(MapperProcessedNews.NewsArticleTT.TITLEZH)
				, getBytes( n.getZhTitle()) 
				);
		putTT.addColumn( getBytes("T")
				, getBytes(MapperProcessedNews.NewsArticleTT.TITLEEN)
				, getBytes( n.getEnTitle()) 
				);
		putTT.addColumn( getBytes("T")
				, getBytes(MapperProcessedNews.NewsArticleTT.TEXTSRC)
				, getBytes( n.getTextSrc()) 
				);
		putTT.addColumn( getBytes("T")
				, getBytes(MapperProcessedNews.NewsArticleTT.TEXTEN)
				, getBytes( n.getEnTxt()) 
				);
		putTT.addColumn( getBytes("T")
				, getBytes(MapperProcessedNews.NewsArticleTT.TEXTZH)
				, getBytes( n.getZhTxt()) 
				);
		return putTT;
	}
	
	public static Put getPutOA(ProcessedNews n){
		Put putOA = new Put( getBytes( n.getId() ));
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.MEDIATYPE)
				, getBytes( n.getMediaType()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.MEDIATNAME)
				, getBytes( n.getMediaTname()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.MEDIANAMESRC)
				, getBytes( n.getMediaNameSrc()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.MEDIANAMEZH)
				, getBytes( n.getMediaNameZh()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.MEDIANAMEEN)
				, getBytes( n.getMediaNameEn()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.COUNTRYNAMEZH)
				, getBytes( n.getCountryNameZh()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.COUNTRYNAMEEN)
				, getBytes( n.getCountryNameEn()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.PROVINCENAMEZH)
				, getBytes( n.getProvinceNameZh()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.PROVINCENAMEEN)
				, getBytes( n.getProvinceNameEn()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.DISTRICTNAMEZH)
				, getBytes( n.getDistrictNameZh()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.DISTRICTNAMEEN)
				, getBytes( n.getDistrictNameEn()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.MEDIALEVEL)
				, getBytes( n.getMediaLevel()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.CREATED)
				, getBytes( n.getCreated()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.UPDATED)
				, getBytes( n.getUpdated()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.PUBDATE)
				, getBytes( n.getPubdate()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.AUTHOR)
				, getBytes( n.getAuthor()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.LANGUAGECODE)
				, getBytes( n.getLanguageCode()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.LANGUAGETNAME)
				, getBytes( n.getLanguageTname()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.WEBSITEID)
				, getBytes( n.getWebsiteId()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ISORIGINAL)
				, getBytes( n.isOriginal()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.VIEW)
				, getBytes( n.getView()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.URL)
				, getBytes( n.getUrl()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.DOCLENGTH)
				, getBytes( n.getDocLength()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.TRANSFROMM)
				, getBytes( n.getTransFromM()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.PV)
				, getBytes( n.getPv()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.URL)
				, getBytes( n.getUrl()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ISHOME)
				, getBytes( n.isHome()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ISPICTURE)
				, getBytes( n.isPicture()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.COMEFROM)
				, getBytes( n.getComeFrom()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.COMEFROMDB)
				, getBytes( n.getComeFromDb()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.USERTAG)
				, getBytes( n.getUserTag()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.TRANSFER)
				, getBytes( n.getTransfer()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.SIMILARITYID)
				, getBytes( n.getSimilarityId()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.PRODUCTS)
				, getBytes( n.getProducts()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.COMPANIES)
				, getBytes( n.getCompanies()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.CATEGORYID)
				, getBytes( n.getCategoryId()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.CATEGORYNAME)
				, getBytes( n.getCategoryName()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.SENTIMENTID)
				, getBytes( n.getSentimentId()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.SENTIMENTNAME)
				, getBytes( n.getSentimentName()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ZHKEYWORDS)
				, getBytes( n.getZhKeywords()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ENKEYWORDS)
				, getBytes( n.getEnKeywords()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ZHSUMMARY)
				, getBytes( n.getZhSummary()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.ENSUMMARY)
				, getBytes( n.getEnSummary()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.IS_SENSITIVE)
				, getBytes( n.getSensitive()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.SENSITIVE_TYPE)
				, getBytes( n.getSensitiveType()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.SENSITIVE_CLS)
				, getBytes( n.getSensitiveCls()) 
				);
		putOA.addColumn( getBytes("O")
				, getBytes(MapperProcessedNews.NewsArticleOA.DEL_FLAG)
				, getBytes( n.getDelFlag()) 
				);
		return putOA;
	}
	
}
