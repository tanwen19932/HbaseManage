package edu.buaa.nlp.entity.news;

import edu.buaa.nlp.tw.common.ReflectUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;


public class ProcessedNews {

	private String id;

	private int mediaType;// 媒体类型id
	private String mediaTname;// 新闻

	private String websiteId; // 站点id
	private String mediaNameSrc;// 数据源名称，源文
	private String mediaNameZh;// 数据源名称，中文
	private String mediaNameEn;// 数据源名称，英文
	// private int countryId; //国家编码，新的不需要 ? //todo
	private String countryNameZh;
	private String countryNameEn;
	private String provinceNameZh;
	private String provinceNameEn;
	private String districtNameZh;
	private String districtNameEn;

	private int mediaLevel; // 级别

	private String author;
	private String created; // 爬取时间
	private String updated; // 更新时间
	private String pubdate; // 发布时间

	private String languageCode;// en zh，国别是编号还是字符串？ 有问题！
	private String languageTname; // 语言 ， 有问题

	private boolean isOriginal;// 是否原创
	private int view;
	private String url;
	private int docLength;

	private String transFromM;
	private int pv = 0;
	private boolean isHome = false;
	private boolean isPicture = false;
	private String comeFrom = null;
	private String comeFromDb = null;
	private String userTag = "";

	// 处理后的内容
	private int transfer = 0; // 转载量
	private String similarityId = ""; // 相似新闻Id

	private String products = ""; // 文档中出现的产品名列表，用英文分号;分隔
	private String companies = ""; // 文档中出现的商家名列表，用英文分号;分隔

	private int categoryId; // 分类 ： 0-8
	private String categoryName; // 分类名称 ，是否需要？
	private int sentimentId; // 情感id：情感分值，-10 ~ 10
	private String sentimentName; // 情感名称：是否需要？

	private String zhKeywords; // 中文关键词列表，用英文分号;分隔；只用于存储中文文本关键词
	private String enKeywords; // 英文关键词列表，用英文分号;分隔；其他语言存储英文关键词
	private String zhSummary; // 中文摘要
	private String enSummary; // 英文摘要

	private String titleSrc; // 标题（源文）
	private String zhTitle; // 标题（中文）
	private String zhTitleSeg; // 标题（中文分词）
	private String enTitle; // 标题（英文）

	private String textSrc; // 正文内容（源文）
	private String zhTxt; // 正文内容（中文）
	private String zhTxtSeg; // 正文内容（中文分词）
	private String enTxt; // 正文内容（英文）

	private int Sensitive = 0; // 是否敏感，默认为0
	private int sensitiveType = 0; // 敏感类型 10：websiteid, 20-敏感词；30-墙网站
	private String sensitiveCls = null; // 敏感分类
	private int delFlag = 0;
	
	
	
	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public static ProcessedNews getFromJSONObject(JSONObject newsJO) {
		ProcessedNews news = new ProcessedNews();
		Field[] fields = news.getClass().getDeclaredFields();
		for (Field field : fields) {
			String value = field.getName();
			if (newsJO.has( value )) {
				try {
					ReflectUtil.invokeObjSetMethod( news, field, newsJO.get( value ) );
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return news;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaTname() {
		return mediaTname;
	}

	public void setMediaTname(String mediaTname) {
		this.mediaTname = mediaTname;
	}

	public String getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}

	public String getMediaNameSrc() {
		return mediaNameSrc;
	}

	public void setMediaNameSrc(String mediaNameSrc) {
		this.mediaNameSrc = mediaNameSrc;
	}

	public String getMediaNameZh() {
		return mediaNameZh;
	}

	public void setMediaNameZh(String mediaNameZh) {
		this.mediaNameZh = mediaNameZh;
	}

	public String getMediaNameEn() {
		return mediaNameEn;
	}

	public void setMediaNameEn(String mediaNameEn) {
		this.mediaNameEn = mediaNameEn;
	}

	public String getCountryNameZh() {
		return countryNameZh;
	}

	public void setCountryNameZh(String countryNameZh) {
		this.countryNameZh = countryNameZh;
	}

	public String getCountryNameEn() {
		return countryNameEn;
	}

	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}

	public int getMediaLevel() {
		return mediaLevel;
	}

	public void setMediaLevel(int mediaLevel) {
		this.mediaLevel = mediaLevel;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageTname() {
		return languageTname;
	}

	public void setLanguageTname(String languageTname) {
		this.languageTname = languageTname;
	}

	public boolean isOriginal() {
		return isOriginal;
	}

	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDocLength() {
		return docLength;
	}

	public void setDocLength(int docLength) {
		this.docLength = docLength;
	}

	public String getTransFromM() {
		return transFromM;
	}

	public void setTransFromM(String transFromM) {
		this.transFromM = transFromM;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	public boolean isPicture() {
		return isPicture;
	}

	public void setPicture(boolean isPicture) {
		this.isPicture = isPicture;
	}

	public int getTransfer() {
		return transfer;
	}

	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}

	public String getSimilarityId() {
		return similarityId;
	}

	public void setSimilarityId(String similarityId) {
		this.similarityId = similarityId;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getCompanies() {
		return companies;
	}

	public void setCompanies(String companies) {
		this.companies = companies;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
		switch (categoryId) {
		case 1:
			this.categoryName = "政治";
			break;
		case 2:
			this.categoryName = "经济";
			break;
		case 3:
			this.categoryName = "文化";
			break;
		case 4:
			this.categoryName = "体育";
			break;
		case 5:
			this.categoryName = "军事";
			break;
		case 6:
			this.categoryName = "社会";
			break;
		case 7:
			this.categoryName = "科技";
			break;
		case 8:
			this.categoryName = "综合";
			break;
		default:
			break;
		}
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getSentimentId() {
		return sentimentId;
	}

	public void setSentimentId(int sentimentId) {
		this.sentimentId = sentimentId;
	}

	public String getSentimentName() {
		return sentimentName;
	}

	public void setSentimentName(String sentimentName) {
		this.sentimentName = sentimentName;
	}

	public String getZhKeywords() {
		return zhKeywords;
	}

	public void setZhKeywords(String zhKeywords) {
		this.zhKeywords = zhKeywords;
	}

	public String getEnKeywords() {
		return enKeywords;
	}

	public void setEnKeywords(String enKeywords) {
		this.enKeywords = enKeywords;
	}

	public String getZhSummary() {
		return zhSummary;
	}

	public void setZhSummary(String zhSummary) {
		this.zhSummary = zhSummary;
	}

	public String getEnSummary() {
		return enSummary;
	}

	public void setEnSummary(String enSummary) {
		this.enSummary = enSummary;
	}

	public String getTitleSrc() {
		return titleSrc;
	}

	public void setTitleSrc(String title) {
		this.titleSrc = title;
	}

	public String getZhTitle() {
		return zhTitle;
	}

	public void setZhTitle(String zhTitle) {
		this.zhTitle = zhTitle;
	}

	public String getZhTitleSeg() {
		return zhTitleSeg;
	}

	public void setZhTitleSeg(String zhTitleSeg) {
		this.zhTitleSeg = zhTitleSeg;
	}

	public String getEnTitle() {
		return enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	public String getTextSrc() {
		return textSrc;
	}

	public void setTextSrc(String txt) {
		this.textSrc = txt;
	}

	public String getZhTxt() {
		return zhTxt;
	}

	public void setZhTxt(String zhTxt) {
		this.zhTxt = zhTxt;
	}

	public String getZhTxtSeg() {
		return zhTxtSeg;
	}

	public void setZhTxtSeg(String zhTxtSeg) {
		this.zhTxtSeg = zhTxtSeg;
	}

	public String getEnTxt() {
		return enTxt;
	}

	public void setEnTxt(String enTxt) {
		this.enTxt = enTxt;
	}

	public String getProvinceNameZh() {
		return provinceNameZh;
	}

	public void setProvinceNameZh(String provinceNameZh) {
		this.provinceNameZh = provinceNameZh;
	}

	public String getProvinceNameEn() {
		return provinceNameEn;
	}

	public void setProvinceNameEn(String provinceNameEn) {
		this.provinceNameEn = provinceNameEn;
	}

	public String getDistrictNameZh() {
		return districtNameZh;
	}

	public void setDistrictNameZh(String districtNameZh) {
		this.districtNameZh = districtNameZh;
	}

	public String getDistrictNameEn() {
		return districtNameEn;
	}

	public void setDistrictNameEn(String districtNameEn) {
		this.districtNameEn = districtNameEn;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getComeFromDb() {
		return comeFromDb;
	}

	public void setComeFromDb(String comeFromDb) {
		this.comeFromDb = comeFromDb;
	}

	public String getUserTag() {
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	public int getSensitive() {
		return Sensitive;
	}

	public void setSensitive(int sensitive) {
		Sensitive = sensitive;
	}

	public int getSensitiveType() {
		return sensitiveType;
	}

	public void setSensitiveType(int sensitiveType) {
		this.sensitiveType = sensitiveType;
	}

	public String getSensitiveCls() {
		return sensitiveCls;
	}

	public void setSensitiveCls(String sensitiveCls) {
		this.sensitiveCls = sensitiveCls;
	}

	@Override
	public String toString() {
		return "ProcessedNews [id=" + id + ", mediaType=" + mediaType + ", mediaTname=" + mediaTname + ", websiteId="
				+ websiteId + ", mediaNameSrc=" + mediaNameSrc + ", mediaNameZh=" + mediaNameZh + ", mediaNameEn="
				+ mediaNameEn + ", countryNameZh=" + countryNameZh + ", countryNameEn=" + countryNameEn
				+ ", provinceNameZh=" + provinceNameZh + ", provinceNameEn=" + provinceNameEn + ", districtNameZh="
				+ districtNameZh + ", districtNameEn=" + districtNameEn + ", mediaLevel=" + mediaLevel + ", author="
				+ author + ", created=" + created + ", updated=" + updated + ", pubdate=" + pubdate + ", languageCode="
				+ languageCode + ", languageTname=" + languageTname + ", isOriginal=" + isOriginal + ", view=" + view
				+ ", url=" + url + ", docLength=" + docLength + ", transFromM=" + transFromM + ", pv=" + pv
				+ ", isHome=" + isHome + ", isPicture=" + isPicture + ", comeFrom=" + comeFrom + ", comeFromDb="
				+ comeFromDb + ", userTag=" + userTag + ", transfer=" + transfer + ", similarityId=" + similarityId
				+ ", products=" + products + ", companies=" + companies + ", categoryId=" + categoryId
				+ ", categoryName=" + categoryName + ", sentimentId=" + sentimentId + ", sentimentName=" + sentimentName
				+ ", zhKeywords=" + zhKeywords + ", enKeywords=" + enKeywords + ", zhSummary=" + zhSummary
				+ ", enSummary=" + enSummary + ", titleSrc=" + titleSrc + ", zhTitle=" + zhTitle + ", zhTitleSeg="
				+ zhTitleSeg + ", enTitle=" + enTitle + ", textSrc=" + textSrc + ", zhTxt=" + zhTxt + ", zhTxtSeg="
				+ zhTxtSeg + ", enTxt=" + enTxt + ", Sensitive=" + Sensitive + ", sensitiveType=" + sensitiveType
				+ ", sensitiveCls=" + sensitiveCls + ", delFlag=" + delFlag + "]";
	}
	
	
}
