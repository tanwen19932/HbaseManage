package product.comment.tempComment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import hbase.hbaseUniversalDao.UniversalDao;
import jxl.*;
import jxl.read.biff.BiffException;
import product.Comment;
import tw.utils.PropertiesUtil;

public class ExelTemp2 {
	
	private static UniversalDao myDao = null;
	static {
		myDao = new UniversalDao("ProductCommentBE", "I" , new Comment());
	}
	static String rootPath=ExelTemp2.class.getResource("/").getPath()+"properties/";
	static Properties lanPro = null;
	static
	{
		lanPro  = PropertiesUtil.getProp(rootPath + "lan.properties");
	}
	
	
	public static void insert() throws BiffException, IOException {
		WorkbookSettings workbookSettings = new WorkbookSettings();
		workbookSettings.setEncoding("ISO-8859-1"); //解决中文乱码，或GBK
		InputStream stream = new FileInputStream(rootPath+"TempCision.xls");
		Workbook book = Workbook.getWorkbook(stream,workbookSettings);
		Sheet sheet = book.getSheet(0);
		for (int i = 1; i < sheet.getRows(); i++) {
			// 创建一个数组 用来存储每一列的值
			String[] str = new String[11];
			// 列数
			for (int j = 0; j < 11; j++) {
				// 获取第i行，第j列的值
				Cell cell = sheet.getCell(j, i);
				str[j] = cell.getContents().trim() ;
				if(j == 10) str[j] = cell.getContents();
			}
			System.err.println(i);
		}
		book.close();
	}
	
	public static void main(String[] args) throws BiffException, IOException {
		insert();
	}
}
