package product;

public class Product {
	private int productId = 0;
	private String productName;
	private String industryName;
	private String companyName;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	
	
}
