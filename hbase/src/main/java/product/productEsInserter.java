package product;

import es.client.ESClient;
import hbase.hbaseUniversalDao.UniversalDao;
import net.sf.json.JSONObject;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.net.UnknownHostException;

public class productEsInserter {
	static final ThreadLocal<Client> client = new ThreadLocal<Client>() {
		@Override
		protected Client initialValue() {
			try {
				return ESClient.getClient("es", "192.168.55.10", 9200);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return null;
		}
	};
	public static void insertCompany(Product product
	)
			throws InterruptedException, UnknownHostException {
		JSONObject jsonQuery=new JSONObject();
		String id  = String.valueOf(product.getProductId());
		jsonQuery.put("id", id);
		jsonQuery.put("companyName", product.getCompanyName());
		jsonQuery.put("industryName", product.getIndustryName());
		jsonQuery.put("productName", product.getProductName());


		IndexRequestBuilder indexRequestBuilder = client.get().prepareIndex("product", "product", id);
		indexRequestBuilder.setSource(jsonQuery.toString());
		Thread.sleep(500);
	}
	private static UniversalDao myDao = null;
	static {
		myDao = new UniversalDao("Product", "P" , new Product()){
			@Override
			public void handleAll(Object obj) {
				// TODO Auto-generated method stub
                Product product = (Product ) obj;
				try {
					insertCompany(product);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	public static void main(String[] args) throws IOException {
		myDao.getAll();
	}
}
