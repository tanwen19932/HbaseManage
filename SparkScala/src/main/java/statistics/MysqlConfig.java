package statistics;

import java.sql.Connection;
import java.sql.DriverManager;


public class MysqlConfig {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String JDBC_URL = "jdbc:mysql://192.168.55.107:3306/buaa?characterEncoding=utf8";
	public static final String JDBC_USER = "root";
	public static final String JDBC_PWD = "Test@123.cn";
	static Connection connection = null;
	
	public static Connection getConnection() throws Exception {
		if(connection==null || !connection.isValid(2) || connection.isClosed()){
			try {
				connection.close();
			} catch (Exception e) {
			}
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PWD);
		}
		return connection;
	}
	
}
