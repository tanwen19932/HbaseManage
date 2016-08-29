package hbase.dicToHbase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import hbase.util.HbaseUtil;
import tw.utils.MySQLConnect;

public class Dic {
	
//	static Configuration conf = HbaseUtil.getConf();
	
	public static void createCountry() {
		try {
			String tablename = "Country";
			String[] familys = { "C" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertCountry() throws Exception {
		createCountry();
		Connection mysqlCon = MySQLConnect.getConnection();
		String sql = "select * from tb_country";
		ResultSet rs = MySQLConnect.select(sql, mysqlCon);
		while (rs.next() ) {
			String id = String.valueOf( rs.getInt(1) ) ;
			HbaseUtil.insertData("Country", id, "C", "id", rs.getString(1));
			HbaseUtil.insertData("Country", id, "C", "countryNameEn", rs.getString(2));
			HbaseUtil.insertData("Country", id, "C", "countryNameZh", rs.getString(3));
			HbaseUtil.insertData("Country", id, "C", "countryNameAbbr", rs.getString(4));
			HbaseUtil.insertData("Country", id, "C", "timeLag", rs.getString(5));
		}
	}
	
	public static void createLanguage() {
		try {
			String tablename = "Language";
			String[] familys = { "L" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertLanguage() throws Exception {
		createLanguage();
		Connection mysqlCon = MySQLConnect.getConnection();
		String sql = "select * from tb_language";
		ResultSet rs = MySQLConnect.select(sql, mysqlCon);
		while (rs.next() ) {
			String id = String.valueOf( rs.getInt(1) ) ;
			HbaseUtil.insertData("Language", id, "L", "languageId", rs.getString(1));
			HbaseUtil.insertData("Language", id, "L", "languageTname", rs.getString(2));
			HbaseUtil.insertData("Language", id, "L", "languageCode", rs.getString(3));
		}
	}
	
	public static void createMedia() {
		try {
			String tablename = "Media";
			String[] familys = { "M" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertMedia() throws Exception {
		createMedia();
		Connection mysqlCon = MySQLConnect.getConnection();
		String sql = "select * from tb_media";
		ResultSet rs = MySQLConnect.select(sql, mysqlCon);
		while (rs.next() ) {
			String id = String.valueOf( rs.getInt(1) ) ;
			HbaseUtil.insertData("Media", id, "M", "mediaType", rs.getString(1));
			HbaseUtil.insertData("Media", id, "M", "mediaTName", rs.getString(2));
			HbaseUtil.insertData("Media", id, "M", "fatherId", rs.getString(3));
		}
	}
	
	public static void createCity() {
		try {
			String tablename = "City";
			String[] familys = { "C" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertCity() throws Exception {
		createCity();
		Connection mysqlCon = MySQLConnect.getConnection();
		String sql = "select * from tb_city";
		ResultSet rs = MySQLConnect.select(sql, mysqlCon);
		while (rs.next() ) {
			String id = String.valueOf( rs.getInt(1) ) ;
			HbaseUtil.insertData("City", id, "C", "cityId", rs.getString(1));
			HbaseUtil.insertData("City", id, "C", "cityNameZh", rs.getString(2));
			HbaseUtil.insertData("City", id, "C", "cityNameEn", rs.getString(3));
			HbaseUtil.insertData("City", id, "C", "fatherId", rs.getString(4));
		}
	}
	
	public static void createMediaSrc() {
		try {
			String tablename = "MediaSrc";
			String[] familys = { "M" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertMediaSrc() throws SQLException, ClassNotFoundException  {
		createMediaSrc();
		Connection mysqlCon = MySQLConnect.getConnection();
		String sql = "select * from tb_mediaSrc";
		ResultSet rs = MySQLConnect.select(sql, mysqlCon);
		while (rs.next() ) {
			String id = rs.getString(1) ;
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaSrcId", rs.getString(1));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaNameSrc", rs.getString(2));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaNameZh", rs.getString(3));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaNameEn", rs.getString(4));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaLevel", rs.getString(5));
			HbaseUtil.insertData("MediaSrc", id, "M", "url", rs.getString(6));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaCountryZh", rs.getString(7));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaCountryEn", rs.getString(8));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaProvinceZh", rs.getString(9));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaProvinceEn", rs.getString(10));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaDistrictZh", rs.getString(11));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaDistrictEn", rs.getString(12));
			HbaseUtil.insertData("MediaSrc", id, "M", "mediaIndustryId", rs.getString(13));
			HbaseUtil.insertData("MediaSrc", id, "M", "languageCode", rs.getString(14));
			HbaseUtil.insertData("MediaSrc", id, "M", "languageTName", rs.getString(15));
		}
	}
	
	public static void createCategory() {
		try {
			String tablename = "Category";
			String[] familys = { "C" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insertCategory() throws Exception {
		createCategory();
		HbaseUtil.insertData("Category", "1", "C", "categoryId", "1");
		HbaseUtil.insertData("Category", "1", "C", "categoryName", "政治");
		HbaseUtil.insertData("Category", "2", "C", "categoryId", "2");
		HbaseUtil.insertData("Category", "2", "C", "categoryName", "经济");
		HbaseUtil.insertData("Category", "3", "C", "categoryId", "3");
		HbaseUtil.insertData("Category", "3", "C", "categoryName", "文化");
		HbaseUtil.insertData("Category", "4", "C", "categoryId", "4");
		HbaseUtil.insertData("Category", "4", "C", "categoryName", "体育");
		HbaseUtil.insertData("Category", "5", "C", "categoryId", "5");
		HbaseUtil.insertData("Category", "5", "C", "categoryName", "军事");
		HbaseUtil.insertData("Category", "6", "C", "categoryId", "6");
		HbaseUtil.insertData("Category", "6", "C", "categoryName", "社会");
		HbaseUtil.insertData("Category", "7", "C", "categoryId", "7");
		HbaseUtil.insertData("Category", "7", "C", "categoryName", "科技");
		HbaseUtil.insertData("Category", "8", "C", "categoryId", "8");
		HbaseUtil.insertData("Category", "8", "C", "categoryName", "综合");
		HbaseUtil.insertData("Category", "999", "C", "categoryId", "999");
		HbaseUtil.insertData("Category", "999", "C", "categoryName", "其他");
	}
	
	public static void main(String[] args) throws Exception  {
		
//		HbaseUtil.deleteTable("Country");
		insertCountry();
//		HbaseUtil.getAllRecord("Country");
//				HbaseUtil.getAllRecord("Country");
		
//		HbaseUtil.deleteTable("Language");		
		insertLanguage();
		
//		HbaseUtil.deleteTable("Media");
		insertMedia();
		
//		HbaseUtil.deleteTable("Category");
		insertCategory();
		
//		HbaseUtil.deleteTable("City");
		insertCity();

//		HbaseUtil.deleteTable("MediaSrc");
//		insertMediaSrc();
		
	}
	public void createTables() throws Exception {
		HbaseUtil.deleteTable("Special");
		String tablename = "Special";
		String[] familys = { "S" };
		HbaseUtil.createTable(tablename, familys);
	}
}
