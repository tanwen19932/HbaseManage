package product.product.hbase;

import hbase.hbaseUniversalDao.Inserter;
import hbase.hbaseUniversalDao.UniversalDao;
import hbase.util.HbaseUtil;
import product.Product;

public class ProductInsert extends Inserter{
	private static UniversalDao myDao = null;
	static {
		myDao = new UniversalDao("Product", "P" , new Product());
	}
	
	public ProductInsert() {
		super(myDao);
	}
	
	public void  create() {
		try {
			String tablename = "Product";
			String[] familys = { "P" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ProductInsert productInsert = new ProductInsert();
		productInsert.create();
		
		
		
//		productInsert.start();
//		HbaseUtil.getAllRecord("Product");
//		HbaseUtil.deleteTable("Product");
	}
}
