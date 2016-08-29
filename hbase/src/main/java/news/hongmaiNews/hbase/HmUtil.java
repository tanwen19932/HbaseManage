package news.hongmaiNews.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HmUtil {
	protected static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	protected static String JDBC_URL = "jdbc:mysql://localhost:3306/jwbasebj_318?characterEncoding=utf8";
	protected static String JDBC_USER = "root";
	protected static String JDBC_PWD = "123456";

	protected static Connection connection = null;

	public static Connection getConnection() throws Exception {
		if (connection == null) {
			connect();
		}
		return connection;
	}
	public static void connect() throws Exception {
		Class.forName(JDBC_DRIVER);
		connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
	}
	
	public static String getHongMaiLang(String language) {
		language = language.trim();
		if(language.equals("简体中文")||language.equals("繁体中文")){
			return "中文";
		}
		if( language.equals("俄罗斯语") ){
			return  "俄语";
		}
		if( language.equals("印度尼西亚语") ){
			return  "印地语";
		}
		if(selectS("select * from tb_language where name = '"+language+"'") != null){
			return language;
		}
		System.err.println(language);
		return "其他";
	}
	
	public static String selectS(String SQL) {
		// System.out.println(SQL);
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
