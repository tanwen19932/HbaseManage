package test;

import java.io.Serializable;

public class Keyword  implements Serializable, Comparable<Keyword>{

	private Long id;
	private String nameSrc;
	private Long newsId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNewsId() {
		return newsId;
	}
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	public String getNameSrc() {
		return nameSrc;
	}
	public void setNameSrc(String nameSrc) {
		this.nameSrc = nameSrc;
	}
	/*@Override
	public boolean equals(Object obj) {
		if(this==null) throw new NullPointerException();
		if(obj==null) return false;
		if(this==obj) return true;
		if(!(obj instanceof TGeisNewsKeyword)) return false;
		TGeisNewsKeyword k=(TGeisNewsKeyword) obj;
		if(nameSrc==k.nameSrc) return true;
		if(nameSrc!=null && nameSrc.equals(k.nameSrc)) return true;
		return false;
	}*/
	@Override
	public boolean equals(Object obj) {
		if(this==null) throw new NullPointerException();
		if(obj==null) return false;
		if(this==obj) return true;
		if(!(obj instanceof Keyword)) return false;
		Keyword k=(Keyword) obj;
		if(nameSrc==k.nameSrc && newsId==k.newsId) return true;
		if(nameSrc!=null && nameSrc.equals(k.nameSrc) && newsId.equals(k.newsId)) return true;
		return false;
	}
	
	@Override
	public int compareTo(Keyword o) {
		return newsId.intValue()-o.newsId.intValue();
	}
	
	public static void main(String[] args) {
		String txt="朝鲜发射fd卫星safsdaf";
		if(!((txt.contains("朝鲜")  && txt.contains("卫星")) || (txt.contains("korea") && txt.contains("statillite"))))System.out.println("continue");
		System.out.println("true");
	}
}
