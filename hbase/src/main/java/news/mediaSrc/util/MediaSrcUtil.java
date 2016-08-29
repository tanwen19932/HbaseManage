package news.mediaSrc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import tw.utils.PropertiesUtil;

public class MediaSrcUtil {
	
	static Properties properties = PropertiesUtil.getProp("mediaSrc.properties");
	static Map<String ,Map<String,String> > mediaSrcMap = null;
	
	private MediaSrcUtil() {
	}
	public static Map<String ,Map<String,String> > getInstance() throws SQLException{
		if(mediaSrcMap==null){
			getMediaMap();
		}
		return mediaSrcMap;
	}
	
	private synchronized static void getMediaMap() throws SQLException{
		if(mediaSrcMap == null ){
			mediaSrcMap = new HashMap<>();
			
			String mediaNameSrc = null;
			String mediaNameZh = null;
			String mediaNameEn = null;
			String countryNameZh =null;
			String countryNameEn = null;
			String districtNameZh = null;
			String districtNameEn = null;
			
			Connection mysqlCon = DriverManager.getConnection(properties.getProperty("JDBC_URL"),properties.getProperty("JDBC_USER"),properties.getProperty("JDBC_PWD"));;
			PreparedStatement preparedStatement2 = null;
			ResultSet mediaSrcRs = null;
			try{
				String sql2 = "select  * from  "+ properties.getProperty("TABLE");
				try {
					preparedStatement2 = mysqlCon.prepareStatement(sql2);
					mediaSrcRs = preparedStatement2.executeQuery();
				} catch (Exception e) {
					e.printStackTrace();
				}
				while( mediaSrcRs.next() ){
					Map<String, String> valueMap = new HashMap<>();
					mediaNameZh = mediaSrcRs.getString("mediaNameZh");
					valueMap.put("mediaNameZh", mediaNameZh );
					mediaNameSrc = mediaSrcRs.getString("mediaNameSrc");
					valueMap.put("mediaNameSrc", mediaNameSrc );
					mediaNameEn = mediaSrcRs.getString("mediaNameEn");
					valueMap.put("mediaNameEn", mediaNameEn );;
					if(mediaSrcRs.getString(5) == null){
						System.out.println();
					}
					valueMap.put("mediaLevel", mediaSrcRs.getString("mediaLevel") ); //级别
					
					countryNameZh =mediaSrcRs.getString("countryNameZh");
					valueMap.put("countryNameZh", countryNameZh );
					countryNameEn = mediaSrcRs.getString("countryNameEn");
					valueMap.put("countryNameEn", countryNameEn );
					districtNameZh = mediaSrcRs.getString("districtNameZh");
					valueMap.put("districtNameZh", districtNameZh );
					districtNameEn = mediaSrcRs.getString("districtNameEn");
					valueMap.put("districtNameEn", districtNameEn );
					mediaSrcMap.put(mediaSrcRs.getString("domainName"), valueMap);
					valueMap = null;
				}
				
				
			}catch (Exception e){
				System.err.println(mediaNameSrc+"数据源未在数据源库 ！错误！");
			}finally {
				if( mediaSrcRs != null ){
					mediaSrcRs.close();
					mediaSrcRs = null;
				}
				if( preparedStatement2 != null ){
					preparedStatement2.close();
					preparedStatement2 = null;
				}
				if( mysqlCon != null ){
					mysqlCon.close();
					mysqlCon = null;
				}
			}
		}
	
	}

}
