package product;

import hbase.hbaseUniversalDao.Inserter;
import hbase.hbaseUniversalDao.UniversalDao;
import hbase.util.HbaseUtil;

public class CompanyInserter extends Inserter{
	private static UniversalDao myDao = null;
	static {
		myDao = new UniversalDao("Company", "C" , new Company());
	}
	
	public CompanyInserter() {
		super(myDao);
	}
	public CompanyInserter(String proName) {
		super(myDao , proName);
	}
	public void  create() {
		try {
			String tablename = "Company";
			String[] familys = { "C" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		CompanyInserter commentInserter = new CompanyInserter();
		commentInserter.create();
		commentInserter.start();
		
//		HbaseUtil.getAllRecord("ProductCommentBE");
//		HbaseUtil.deleteTable("ProductCommentBE");
	}
}
