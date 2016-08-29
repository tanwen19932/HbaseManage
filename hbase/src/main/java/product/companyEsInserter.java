//package product;
//
//import edu.buaa.nlp.es.product.Mapper.FieldCompany;
//import hbase.hbaseUniversalDao.UniversalDao;
//
//import java.io.IOException;
//
//import edu.buaa.nlp.es.product.SearchBuilder;
//import net.sf.json.JSONObject;
//
//public class companyEsInserter {
//	public static void insertCompany(Company company) throws InterruptedException
//	{
//		JSONObject jsonQuery=new JSONObject();
//		jsonQuery.put(FieldCompany.UUID, company.getCompanyId());
//		jsonQuery.put(FieldCompany.COMPANY_NAME, company.getCompanyName());
//		jsonQuery.put(FieldCompany.COMPANY_ALIAS, company.getCompanyAlias());
//		jsonQuery.put(FieldCompany.INDUSTRY_NAME, company.getIndustryName());
//		jsonQuery.put(FieldCompany.COMPANY_INTRO, company.getCompanyIntro());
//		jsonQuery.put(FieldCompany.COMPANY_LOGO, company.getCompanyLogo());
//
//		SearchBuilder sb=new SearchBuilder();
//		sb.insertCompany(jsonQuery);
//		sb.close();
//		Thread.sleep(500);
//	}
//	private static UniversalDao myDao = null;
//	static {
//		myDao = new UniversalDao("Company", "C" , new Company()){
//			@Override
//			public void handleAll(Object obj) {
//				// TODO Auto-generated method stub
//				Company company = (Company ) obj;
//				try {
//					insertCompany(company);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//	}
//
//	public static void main(String[] args) throws IOException {
//		myDao.getAll();
//	}
//}
