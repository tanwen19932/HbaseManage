package product.comment.excel;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import hbase.hbaseUniversalDao.UniversalDao;
import product.Comment;

public class HbaseCommentInserter extends DirInserter{
	UniversalDao universalDao ;
	String filePath;
	Comment comment;
	int sheetNum;
	int[] columns;
	AtomicInteger count = new AtomicInteger();
	
	String website ;
	String industryName ;
//	String 
	
	
	public void setWebsite(String website) {
		this.website = website;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public HbaseCommentInserter(String tableName , String family ,  String filePath ,int sheetNum ,Comment comment , int ...columns) throws IOException {
		// TODO Auto-generated constructor stub
		universalDao = new UniversalDao(tableName, family, comment);
		this.filePath = filePath;
		this.comment = comment;
		this.filePath = filePath;
		this.sheetNum = sheetNum;
		this.columns = columns;
	}
	
	public HbaseCommentInserter(String tableName , String family , String filePath , Comment comment ,int ...columns) throws IOException{
		this( tableName,  family,   filePath ,0 ,comment,columns);
	}
	
	public void start() {
		insertDir(filePath);
	}

	@Override
	public void insertFile(File file) {
		// TODO Auto-generated method stub
		CommentFromExcel commentFromExcel= null ;
		try {
			commentFromExcel = new CommentFromExcel(file, sheetNum, comment ,columns);
			for (Comment comment : commentFromExcel) {
				if(comment == null) 
					continue;
				comment.setWebsite( website );
				comment.setIndustryName( industryName );
//				System.out.println(comment);
				universalDao.insert(comment);
				System.out.println( "插入成功 " + count.incrementAndGet()+"  " + comment.getCommentId());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(commentFromExcel != null){
				try {
					commentFromExcel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
