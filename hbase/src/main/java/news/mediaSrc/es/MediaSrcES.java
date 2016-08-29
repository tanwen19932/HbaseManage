package news.mediaSrc.es;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.json.JSONObject;

import es.client.ESClient;
import news.DicMap;
import tw.utils.JsonUtil;
import tw.utils.MysqlUniversalDao;
import static tw.utils.StringUtil.*;


public class MediaSrcES {

	public MediaSrcES() {
		// TODO Auto-generated constructor stub
	}

	protected static MediaSrc news = new MediaSrc();
	public static Connection connection;
	static MysqlUniversalDao mysqlUDao;

	static {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String JDBC_URL = "jdbc:mysql://192.168.55.20:3306/jwbase_datacenter?characterEncoding=utf8";
		String JDBC_USER = "yeesight";
		String JDBC_PWD = "Test@123.cn";
		String TABLE_NAME = "newsarticlebe2count";

		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
			mysqlUDao = new MysqlUniversalDao(connection, TABLE_NAME, news);
		} catch (Error | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

	}
	
	/**
	 * @param mediaSrc 数据源信息
	 * @return 是否修复
	 */
	public static boolean fixMediaSrcCountry(MediaSrc mediaSrc){
		if( isNull(mediaSrc.getCountryNameEn()) && isNull( mediaSrc.getCountryNameZh() ) ){
			return false;
		}
		if( isNull(mediaSrc.getCountryNameEn()) && !isNull( mediaSrc.getCountryNameZh() ) ){
			mediaSrc.setCountryNameEn(DicMap.getCountryEn( mediaSrc.getCountryNameZh() ));
		}
		if( !isNull(mediaSrc.getCountryNameEn()) && isNull( mediaSrc.getCountryNameZh() ) ){
			mediaSrc.setCountryNameZh(DicMap.getCountryZh( mediaSrc.getCountryNameEn() ));
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		
		Client client = ESClient.getClient("yeesightNew","192.168.55.45",9300);
		ResultSet rs = mysqlUDao.select("select * from newsarticlebe2count a where a.districtNameZh != 'null'");
		int count = 0;
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		while (rs.next()) {
			MediaSrc mynews = new MediaSrc();
			String id = rs.getString("id");
			mynews.setMediaNameZh(rs.getString("mediaNameZh"));
			mynews.setMediaNameEn(rs.getString("mediaNameEn"));
			mynews.setMediaNameSrc(rs.getString("mediaNameSrc"));
			mynews.setCountryNameZh(rs.getString("countryNameZh"));
			mynews.setCountryNameEn(rs.getString("countryNameEn"));
			mynews.setDistrictNameZh(rs.getString("districtNameZh"));
			mynews.setDistrictNameEn(rs.getString("districtNameEn"));
			mynews.setDomainName(rs.getString("domainName"));
			mynews.setLanguageCode(rs.getString("languageCode"));
			mynews.setLanguageTname(rs.getString("languageTname"));
			mynews.setMediaLevel(rs.getString("mediaLevel"));
			if (mynews.mediaNameZh==null||mynews.mediaNameZh.toLowerCase().equals("null") ) {
				if( mynews.mediaNameSrc!=null && !mynews.mediaNameSrc.toLowerCase().equals("null")){
					mynews.setMediaNameZh( mynews.mediaNameSrc );
				}
				else if ( mynews.mediaNameEn==null||mynews.mediaNameEn.toLowerCase().equals("null") ) {
					mynews.setMediaNameZh( mynews.mediaNameEn );
					mynews.setMediaNameSrc( mynews.mediaNameEn );
				}
				else {
					continue;
				}
			}
			if( mynews.mediaNameSrc == null || mynews.mediaNameSrc.toLowerCase().equals("null")){
				mynews.setMediaNameSrc( mynews.mediaNameZh );
			}
			fixMediaSrcCountry( mynews );
//			System.out.println( mynews.mediaNameZh + " !! " + mynews.mediaNameSrc + " !! " );
			JSONObject json = JsonUtil.getJsonObj(mynews);
			json.put("mediaSrcId", id);
			
			// either use client#prepare, or use Requests# to directly build
			// index/delete requests
			
			bulkRequest.add(client.prepareIndex("news2", "media", id).setSource(json.toString()));
			count++;
			if (count == 100) {
				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
				bulkRequest = null;
				bulkRequest = client.prepareBulk(); 
				System.out.println(bulkResponse.getHeaders());
				count = 0;
			}
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		System.out.println(bulkResponse.getHeaders());
		client.close();
	}
}