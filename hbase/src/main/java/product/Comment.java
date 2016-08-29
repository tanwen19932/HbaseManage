package product;

import hbase.util.Murmurs;

public class Comment {
	
	private long commentId;
	private int productId = 0;
	private int userId = 0;
	private String userName = null;
	private String userLoc = null;
	private String userRegistTime = null;
	
	private String contentSrc;
	private String createdDate = null;
	private String referenceName; 
	private String website = null;
	private float score = 0 ;
	private String buyTime = null;
	private int help = 0;
	private int unhelp = 0;
	private String lauguageCode = "zh";
	private String industryName =null;
	private String companyName = null;
	private String comeFrom = null;
	
	
	public Comment() {
		
	}

	public Comment(int commentId, int productId, int userId, String userName, String userLoc, String userRegistTime,
			String contentSrc, String createdDate, String referenceName, String website, float score, String buyTime,
			int help, int unhelp, String lauguageCode, String industryName, String companyName, String comeFrom) {
		super();
		this.commentId = commentId;
		this.productId = productId;
		this.userId = userId;
		this.userName = userName;
		this.userLoc = userLoc;
		this.userRegistTime = userRegistTime;
		this.contentSrc = contentSrc;
		this.createdDate = createdDate;
		this.referenceName = referenceName;
		this.website = website;
		this.score = score;
		this.buyTime = buyTime;
		this.help = help;
		this.unhelp = unhelp;
		this.lauguageCode = lauguageCode;
		this.industryName = industryName;
		this.companyName = companyName;
		this.comeFrom = comeFrom;
	}



//	public Comment( String commentId , int productId , int userId , String content ,java.util.Date createdTime , String referenceName , String website) {
//		this.commentId = commentId;
//		this.productId = productId;
//		this.userId = userId;
//		this.content = content; 
//		this.createdTime = new Timestamp(createdTime.getTime()) ;
//		this.referenceName = referenceName;
//		this.website = website;
//	}
//	public Comment( String commentId , int productId , int userId , String content ,java.util.Date createdTime , String referenceName , String website , float score, java.util.Date buyTime ) {
//		this.commentId = commentId;
//		this.productId = productId;
//		this.userId = userId;
//		this.content = content; 
//		this.createdTime = new Timestamp(createdTime.getTime()) ;
//		this.referenceName = referenceName;
//		this.website = website;
//		this.score = score ;
//		this.buyTime = new Timestamp(buyTime.getTime());
//	}



	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getReferenceName() {
		return referenceName;
	}
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	public int getIsHelpful() {
		return help;
	}
	public void setIsHelpful(int isHelpful) {
		this.help = isHelpful;
	}
	public int getIsNotHelpful() {
		return unhelp;
	}
	public void setIsNotHelpful(int isNotHelpful) {
		this.unhelp = isNotHelpful;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public long getCommentId() {
		String a = ""+commentId;
		if( a.equals("0") ){
			return Murmurs.hash(contentSrc+createdDate+referenceName+website+industryName);
		}
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public int getHelp() {
		return help;
	}

	public void setHelp(int help) {
		this.help = help;
	}

	public int getUnhelp() {
		return unhelp;
	}

	public void setUnhelp(int unhelp) {
		this.unhelp = unhelp;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		companyName = companyName.trim().toUpperCase();
		int min = -1;
		int a = companyName.indexOf(';');
		min = a==-1?min:min==-1?a:Math.min(a, min);
		a = companyName.indexOf('(');
		min = a==-1?min:min==-1?a:Math.min(a, min);
		a = companyName.indexOf('（');
		min = a==-1?min:min==-1?a:Math.min(a, min);
		if( min != -1 ) companyName = companyName.substring(0,min);
		if( companyName.replaceAll("[a-zA-Z0-9]*", "").length() != 0 )
			companyName=companyName.replaceAll("[a-zA-Z0-9]*", "");
		
		this.companyName = companyName;
	}
	

	public String getComeFrom() {
		return comeFrom;
	}


	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserLoc() {
		return userLoc;
	}



	public void setUserLoc(String userLoc) {
		this.userLoc = userLoc;
	}



	public String getUserRegistTime() {
		return userRegistTime;
	}



	public void setUserRegistTime(String userRegistTime) {
		this.userRegistTime = userRegistTime;
	}



	public String getContentSrc() {
		return contentSrc;
	}



	public void setContentSrc(String contentSrc) {
		this.contentSrc = contentSrc;
	}


	public String getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLauguageCode() {
		return lauguageCode;
	}

	public void setLauguageCode(String lauguageCode) {
		this.lauguageCode = lauguageCode;
	}


	
}