package product.comment.excel;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import hbase.hbaseUniversalDao.UniversalDao;

public class HbaseInserter extends DirInserter{
	UniversalDao universalDao ;
	ObjectFromExcel ofe ;
	String filePath;
	Object obj;
	int sheetNum;
	int[] columns;
	
	public HbaseInserter(String tableName , String family ,  String filePath ,int sheetNum ,Object obj , int ...columns) throws IOException {
		// TODO Auto-generated constructor stub
		universalDao = new UniversalDao(tableName, family, obj);
		this.filePath = filePath;
		this.obj = obj;
		this.filePath = filePath;
		this.sheetNum = sheetNum;
		this.columns = columns;
		
	}
	
	public HbaseInserter(String tableName , String family , String filePath , Object obj ,int ...columns) throws IOException{
		this( tableName,  family,   filePath ,0 ,obj,columns);
	}
	
	
	public void start() {
		insertDir(filePath);
	}

	@Override
	public void insertFile(File file) {
		// TODO Auto-generated method stub
		try {
			this.ofe = new ObjectFromExcel(file, sheetNum, obj);
			for (Object object : ofe) {
				System.out.println(object);
//				universalDao.insert(object);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(ofe != null){
				try {
					ofe.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
