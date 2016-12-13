package edu.buaa.nlp.entity.news;

public class MapperProcessedNews {

	public static final String ROWKEY="rowkey";
	public static final String FAMILY="family";
	public static final String QUALIFIER="qualifier";
	public static final String VALUE="value";
	
	public class NewsArticleTT{
		public static final String TITLESRC="titleSrc";
		public static final String TITLEZH="titleZh";
		public static final String TITLEEN="titleEn";
		public static final String TEXTZH="textZh";
		public static final String TEXTEN="textEn";
		public static final String TEXTSRC="textSrc";
	}
	
	public class NewsArticleOA{
		public static final String MEDIATYPE="mediaType";
		public static final String MEDIATNAME="mediaTname";
		
		public static final String MEDIANAMESRC="mediaNameSrc";
		public static final String MEDIANAMEZH="mediaNameZh";
		public static final String MEDIANAMEEN="mediaNameEn";
		
		public static final String COUNTRYID="countryId";
		
		public static final String COUNTRYNAMEZH="countryNameZh";
		public static final String COUNTRYNAMEEN="countryNameEn";
		public static final String PROVINCENAMEZH = "provinceNameZh";
		public static final String PROVINCENAMEEN = "provinceNameEn";
		public static final String DISTRICTNAMEZH = "districtNameZh";
		public static final String DISTRICTNAMEEN = "districtNameEn";
		
		
		public static final String MEDIALEVEL="mediaLevel";
		public static final String CREATED="created";
		public static final String UPDATED="updated";
		public static final String PUBDATE="pubdate";
		public static final String AUTHOR="author";
		
		public static final String LANGUAGECODE="languageCode";
		public static final String LANGUAGETNAME="languageTname";
		public static final String WEBSITEID="websiteId";
		public static final String ISORIGINAL="isOriginal";
		public static final String VIEW="view";
		public static final String URL="url";
		public static final String DOCLENGTH="docLength";
		public static final String TRANSFROMM="transFromM";
		public static final String PV="pv";
		public static final String ISHOME="isHome";
		public static final String ISPICTURE="isPicture";
		
		public static final String COMEFROM="comeFrom";
		public static final String COMEFROMDB="comeFromDb";
		public static final String USERTAG="userTag";
		
		public static final String TRANSFER="transfer";
		public static final String SIMILARITYID="similarityId";
		public static final String PRODUCTS="products";
		public static final String COMPANIES="companies";
		public static final String CATEGORYID="categoryId";
		public static final String CATEGORYNAME="categoryName";
		public static final String SENTIMENTID="sentimentId";
		public static final String SENTIMENTNAME="sentimentName";
		public static final String ZHKEYWORDS="keywordsZh";
		public static final String ENKEYWORDS="keywordsEn";
		public static final String ZHSUMMARY="abstractZh";
		public static final String ENSUMMARY="abstractEn";
		
		public static final String IS_SENSITIVE="isSensitive";
		public static final String SENSITIVE_TYPE="sensitiveType";
		public static final String SENSITIVE_CLS="sensitiveCls";

		public static final String ZHTITLESEG="zhTitleSeg";
		public static final String ZHTXTSEG="zhTxtSeg";
		
		public static final String DEL_FLAG="delFlag";
		
	}

	
	public class NewsArticleBE{
		
		public static final String TITLESRC="titleSrc";
		public static final String TEXTSRC="textSrc";
		
		public static final String MEDIATYPE="mediaType";
		public static final String MEDIATNAME="mediaTname";
		public static final String MEDIANAMESRC="mediaNameSrc";
		public static final String MEDIANAMEZH="mediaNameZh";
		public static final String MEDIANAMEEN="mediaNameEn";
		public static final String COUNTRYID="countryId";
		public static final String COUNTRYNAMEZH="countryNameZh";
		public static final String COUNTRYNAMEEN="countryNameEn";
		public static final String PROVINCE_NAME_ZH = "provinceNameZh";
		public static final String PROVINCE_NAME_EN = "provinceNameEn";
		public static final String DISTRICT_NAME_ZH = "districtNameZh";
		public static final String DISTRICT_NAME_EN = "districtNameEn";
		public static final String MEDIALEVEL="mediaLevel";
		public static final String CREATED="created";
		public static final String UPDATED="updated";
		public static final String PUBDATE="pubdate";
		public static final String AUTHOR="author";
		public static final String LANGUAGECODE="languageCode";
		public static final String LANGUAGETNAME="languageTname";
		public static final String WEBSITEID="websiteId";
		public static final String ISORIGINAL="isOriginal";
		public static final String VIEW="view";
		public static final String URL="url";
		public static final String DOCLENGTH="docLength";
		public static final String TRANSFROMM="transFromM";
		public static final String PV="pv";
		public static final String ISHOME="isHome";
		public static final String ISPICTURE="isPicture";
		
		public static final String SIMILARITYID="similarityId";
		
		public static final String COME_FROM = "comeFrom";
		public static final String COME_FROM_DB = "comeFromDb";
		public static final String USER_TAG = "userTag";
		
	}
	
	
}
