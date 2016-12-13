package edu.buaa.nlp.duplicate;
/***
 * 排重相关信息
 * @author Administrator
 *
 */
public class DuplicateInfo {
	
	private Long titleHashcode;
	private Long simHashCode;
	private Long mediaHashcode;
	private String uuid;
	
	
	
//	sbWeb.append(titleHashcode+"\t"+simHashCode+"\t"+mediaHashcode+"\r\n");
//	sbUUID.append(simHashCode+"\t"+uuid+"\r\n");
	
	public DuplicateInfo(Long titleHashcode,Long simHashCode,Long mediaHashcode,String uuid)
	{
		this.titleHashcode = titleHashcode;
		this.simHashCode = simHashCode;
		this.mediaHashcode = mediaHashcode;
		this.uuid = uuid;
	}


	public Long getTitleHashcode() {
		return titleHashcode;
	}



	public void setTitleHashcode(Long titleHashcode) {
		this.titleHashcode = titleHashcode;
	}



	public Long getSimHashCode() {
		return simHashCode;
	}



	public void setSimHashCode(Long simHashCode) {
		this.simHashCode = simHashCode;
	}



	public Long getMediaHashcode() {
		return mediaHashcode;
	}



	public void setMediaHashcode(Long mediaHashcode) {
		this.mediaHashcode = mediaHashcode;
	}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
