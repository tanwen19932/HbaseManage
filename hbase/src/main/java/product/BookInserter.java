package product;

import hbase.hbaseUniversalDao.Inserter;
import hbase.hbaseUniversalDao.MyDao;



public class BookInserter extends Inserter {
	public static MyDao myDao = new BookDao();
	
	public BookInserter() {
		super(myDao);
	}

	public static void main(String[] args) throws Exception {
		BookInserter bookInserter = new BookInserter();
		bookInserter.start();
		
//		HbaseUtil.getAllRecord("ProductBook");
//		HbaseUtil.deleteTable("ProductBook");
	}
}
