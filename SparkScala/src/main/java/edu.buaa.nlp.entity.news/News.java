package edu.buaa.nlp.entity.news;

public class News {
	private String id = null; //byte[] id ;
	private int mediaType ;//媒体类型id
	private String mediaTname = null;// 新闻
	
	private String titleSrc = null;
	private String pubdate = null; 	//发布时间
	private String textSrc = null;
	
	private String websiteId = null;	//站点id
	private String mediaNameSrc  = null;//数据源名称
	private String mediaNameZh = null;
	private String mediaNameEn = null;
	private int mediaLevel ; //级别
	
	private String countryNameZh = null ;
	private String countryNameEn  = null;
	private String provinceNameZh = null;
	private String provinceNameEn = null;
	private String districtNameZh = null;
	private String districtNameEn = null;
	
	
	
	private String languageCode = null;//en zh
	private String languageTname = null;
	
	private String author = null;
	
	private String created = null; 	//爬取时间
	private String updated = null;	//更新时间
	
	private boolean isOriginal ;//是否原创
	
	private int view ;
	private String url = null;
	private int docLength ;
	
	private String transFromM = null;
	private int pv;
	private boolean isHome ;
	private boolean isPicture ;
	
	private String comeFrom = null;
	private String comeFromDb = "";
	private String userTag = "";
	
	private String similarityId=""; //相似新闻Id

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


	public String getTitleSrc() {
		return titleSrc;
	}


	public void setTitleSrc(String titleSrc) {
		this.titleSrc = titleSrc;
	}


	public String getPubdate() {
		return pubdate;
	}


	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}


	public String getTextSrc() {
		return textSrc;
	}


	public void setTextSrc(String textSrc) {
		this.textSrc = textSrc;
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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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


	public boolean isOriginal() {
		return isOriginal;
	}


	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getMediaNameEn() {
		return mediaNameEn;
	}


	public void setMediaNameEn(String mediaNameEn) {
		this.mediaNameEn = mediaNameEn;
	}


	public String getMediaNameZh() {
		return mediaNameZh;
	}


	public void setMediaNameZh(String mediaNameZh) {
		this.mediaNameZh = mediaNameZh;
	}


	public String getLanguageTname() {
		return languageTname;
	}


	public void setLanguageTname(String languageTname) {
		this.languageTname = languageTname;
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




	public void setSimilarityId(String origionUUID) {
		// TODO Auto-generated method stub
		similarityId = origionUUID;
	}




	public String getSimilarityId() {
		return similarityId;
	}	
}
