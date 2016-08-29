package SocialSpecial;

import java.util.Date;

public class SocialSpecial {
	private String specialId;
	private String title;
	private String description;
	private String keyWord;
	private Date registTime;
	private Date activeTime;
	private Date expiredTime;
	private Date arrearageTime;
	private Date deleteTime;
	private int state;
	private boolean analyzed;
	private long startTime;
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Date getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public Date getArrearageTime() {
		return arrearageTime;
	}
	public void setArrearageTime(Date arrearageTime) {
		this.arrearageTime = arrearageTime;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isAnalyzed() {
		return analyzed;
	}
	public void setAnalyzed(boolean analyzed) {
		this.analyzed = analyzed;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
}
