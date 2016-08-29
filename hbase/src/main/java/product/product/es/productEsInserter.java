//package product.product.es;
//
//import edu.buaa.nlp.es.product.Mapper.FieldProduct;
//import hbase.hbaseUniversalDao.UniversalDao;
//import hbase.util.HbaseUtil;
//import edu.buaa.nlp.es.product.SearchBuilder;
//
//import java.io.IOException;
//
//import net.sf.json.JSONObject;
//import product.Product;
//
//public class productEsInserter {
//
//	public static void insertProduct(Product product) throws InterruptedException
//	{
//		JSONObject jsonQuery=new JSONObject();
//		jsonQuery.put(FieldProduct.UUID, product.getProductId());
//		jsonQuery.put(FieldProduct.PRODUCT_NAME, product.getProductName());
//		jsonQuery.put(FieldProduct.INDUSTRY_NAME, product.getIndustryName());
//		jsonQuery.put(FieldProduct.COMPANY_NAME, product.getCompanyName());
//
//		SearchBuilder sb=new SearchBuilder();
//		sb.insertProduct(jsonQuery);
//		sb.close();
//		Thread.sleep(500);
//	}
//	private static UniversalDao myDao = null;
//	static {
//		myDao = new UniversalDao("Product", "P" , new Product()){
//			@Override
//			public void handleAll(Object obj) {
//				// TODO Auto-generated method stub
//				Product product = (Product ) obj;
//				try {
//					insertProduct(product);
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
