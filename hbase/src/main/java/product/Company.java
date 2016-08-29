package product;

public class Company {
	int companyId;
	String companyName;
	String companyAlias;
	String companyIntro;
	String companyLogo;
	String industryName;
	

	public Company() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Company(int companyId, String companyName, String companyAlias, String companyIntro, String companyLogo,
			String industryName) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyAlias = companyAlias;
		this.companyIntro = companyIntro;
		this.companyLogo = companyLogo;
		this.industryName = industryName;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getCompanyIntro() {
		return companyIntro;
	}
	public void setCompanyIntro(String companyIntro) {
		this.companyIntro = companyIntro;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}




	public String getCompanyAlias() {
		return companyAlias;
	}




	public void setCompanyAlias(String companyAlias) {
		this.companyAlias = companyAlias;
	}




	public String getIndustryName() {
		return industryName;
	}




	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
}
