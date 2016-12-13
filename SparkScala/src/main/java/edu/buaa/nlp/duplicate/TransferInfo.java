/**
 * 
 */
package edu.buaa.nlp.duplicate;

/**
 * 转载相关信息
 * @author Administrator
 *	
 */
public class TransferInfo {
	private String uuid;
	private String origionUUID;
	
	public TransferInfo(String uuid,String origionUUID)
	{
		this.uuid = uuid;
		this.origionUUID = origionUUID;
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrigionUUID() {
		return origionUUID;
	}

	public void setOrigionUUID(String origionUUID) {
		this.origionUUID = origionUUID;
	}
	
}
