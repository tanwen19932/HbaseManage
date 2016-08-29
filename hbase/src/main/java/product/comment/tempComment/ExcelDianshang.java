package product.comment.tempComment;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import product.Comment;
import product.Company;
import product.Product;
import tw.utils.ExcelUtil;
import tw.utils.MysqlUniversalDao;

public class ExcelDianshang {
	public static Connection connection ;
	static {
		String	JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String	JDBC_URL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8";
		String	JDBC_USER = "root";
		String	JDBC_PWD = "Test@123.cn";
		
		connection = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PWD);
		} catch (Error|Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static String tryParse(String date) {
		List<String> regexList = new ArrayList<>();
		//By PA Aug 24, 2015
		regexList.add("MMM dd, yyyy");
		regexList.add("MMM dd,HH:mm:ss");
		//Tue, 04/26/2016 - 07:23
		regexList.add("EEEE, MM/dd/yyyy - HH:mm");
		//2016-04-14T12:52:17Z
		regexList.add("yyyy-MM-ddHH:mm:ss");

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		for(int i =0 ;i<regexList.size() ; i++){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat(regexList.get(i),Locale.ENGLISH);
				return sdf2.format( sdf.parse(date) );
			}catch (Exception e){
			}
		}
		return null;
	}
	
	public static void insertDir(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for( int i = 0 ; i < files.length; i++){
				try {
					System.out.println( files[i].getName() );
					if(files[i].isDirectory()){
						insertDir(files[i]);
//						continue;
					}
				    insert(files[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void insert(File file) throws  IOException {
//		UniversalDao commentDao = new UniversalDao("Comment", "P" , new Comment());
		
//		UniversalDao companyDao = new UniversalDao("Company", "P" , new Company());
		MysqlUniversalDao companyDaoMysql = new MysqlUniversalDao(connection, "company", new Company());
		
		
//		UniversalDao productDao = new UniversalDao("Product", "P" , new Product());
		MysqlUniversalDao productDaoMysql = new MysqlUniversalDao(connection, "product", new Product());

		Workbook book = ExcelUtil.getHExcelWorkbook(file);
		Sheet sheet = book.getSheetAt(0);
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			// 创建一个数组 用来存储每一列的值
			Row row = sheet.getRow(i);
			// 列数
			String[] str = new String[row.getLastCellNum()];
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// 获取第i行，第j列的值
				Cell cell = row.getCell(j);
				str[j] = cell.getStringCellValue().trim() ;
			}
			Product product = new Product();
			product.setProductName(str[0]);
			product.setCompanyName(str[1]);
			product.setIndustryName("手机");
			productDaoMysql.insert(product);
			
			Company company = new Company();
			company.setCompanyName(str[1]);
			company.setCompanyAlias(str[2]);
			company.setIndustryName("手机");
			
			Comment comment = new Comment();
			comment.setReferenceName(str[1]);
			comment.setCompanyName(str[1]);
			comment.setCreatedDate(str[5]);
			comment.setContentSrc(str[8]);
			
//			System.out.println(str.toString());
		}
		
		book.close();
	}
	
	public static void main(String[] args) throws  IOException {
		File dir = new File( "E:\\insert\\电商" );
		insertDir(dir);
	}
}
