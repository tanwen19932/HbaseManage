package product.comment.hbase;

import hbase.hbaseUniversalDao.Inserter;
import hbase.hbaseUniversalDao.UniversalDao;
import hbase.util.HbaseUtil;
import product.Comment;

public class CommentInserter extends Inserter{
	private static UniversalDao myDao = null;
	static {
		myDao = new UniversalDao("ProductCommentBE", "P" , new Comment());
	}
	
	public CommentInserter() {
		super(myDao);
	}
	public CommentInserter(String proName) {
		super(myDao , proName);
	}
	public void  create() {
		try {
			String tablename = "ProductCommentBE";
			String[] familys = { "P" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		CommentInserter commentInserter = new CommentInserter("book");
		commentInserter.create();
		commentInserter.start();
		
//		HbaseUtil.getAllRecord("ProductCommentBE");
//		HbaseUtil.deleteTable("ProductCommentBE");
	}
}
