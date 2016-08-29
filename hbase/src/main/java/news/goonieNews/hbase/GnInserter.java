package news.goonieNews.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import news.News;
import news.NewsDao;
import news.hongmaiNews.hbase.CalendarUtil;
import news.hongmaiNews.hbase.HmDicUtil;
import news.mediaSrc.util.MediaSrcUtil;
import tw.utils.HtmlUtil;
import tw.utils.PropertiesUtil;

public class GnInserter {
	
	private static Log LOG = LogFactory.getLog(GnInserter.class);
	private NewsDao newsDao = new NewsDao();
	private Properties properties  = null;
	private String rootPath=CalendarUtil.class.getResource("/").getPath();
	private String filePath = rootPath+"crawler.properties";
	
	protected static Connection connection = null;
	protected static String	 JDBC_DRIVER = "com.mysql.jdbc.Driver";
	protected static String	 JDBC_URL = "jdbc:mysql://192.168.55.20:3306/goonidb?characterEncoding=utf8";
//	protected static String	 JDBC_URL = "jdbc:mysql://192.168.55.20:3306/gooni_new?characterEncoding=utf8";
	protected static String	 JDBC_USER	="root";
	protected static String	 JDBC_PWD	= "Test@123.cn";
	
	protected static Map<String, Map<String,String> > mediaSrcMap ;
	
	
	static{
		try {
			mediaSrcMap = MediaSrcUtil.getInstance();
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PWD);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected static Connection connection20 = null;
	
	public void insertGN() throws ClassNotFoundException, SQLException {
		String key = "guni";
		properties= PropertiesUtil.getProp(filePath);
		int pageSize = 1000;
		int page = 0;
		properties.setProperty(key, "93000");
		int startRow  = Integer.parseInt((String) properties.get(key) );
		int totalCount = startRow;
		while(true){
			String sql = "select a.typeid, a.title , b.publish_time , b.content,a.id , a.source ,a.`language`,a.publish_time ,a.url   from gooniearticle a right join gooniearticledetailed b on a.id = b.pid  where a.`language` = 'zh' and typeid != 6"
					+ " limit " + (pageSize*page+startRow) + "," + pageSize ;
			System.out.println(sql);
			Connection mysqlCon = connection;
			PreparedStatement preparedStatement = mysqlCon.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while(rs.next()){
				totalCount++;
				i++;
				try{
					String id = null; //byte[] id ;
					
					int mediaType = 1 ;//媒体类型id
					String mediaTname = "新闻";// 新闻
					
					String titleSrc = rs.getString(2);
					String pubdate = rs.getString(3).substring(0, 19); 	//发布时间
					String textSrc = rs.getString(4);
					
					String websiteId = rs.getString(5);	//站点id
					
					String mediaNameSrc = null;//数据源名称
					String mediaNameZh = rs.getString(6);
					String mediaNameEn = null;;
					int mediaLevel = 4 ; //级别
					String countryNameZh = null;
					String countryNameEn = null;
					String provinceNameZh = null;
					String provinceNameEn = null;
					String districtNameZh = null;
					String districtNameEn = null;
					String languageCode =null;//en zh
					String languageTname = null;
					
					String url = rs.getString(9);
					Map<String, String > valueMap = mediaSrcMap.get( HtmlUtil.getDomain(url) );
					if(valueMap == null){
						System.err.println("数据源不在 数据源库");
						countryNameZh = "其他";
						countryNameEn = "other";
						
					}
					else{
						mediaNameZh = valueMap.get("mediaNameZh");
						mediaNameSrc = valueMap.get("mediaNameSrc");
						mediaNameEn  = valueMap.get("mediaNameEn");
						String levelString =  valueMap.get("mediaLevel") ;
						if( levelString != null)
							mediaLevel = Integer.valueOf( levelString ); //级别
						countryNameZh = valueMap.get("countryNameZh");
						countryNameEn = valueMap.get("countryNameEn");
						provinceNameZh = valueMap.get("provinceNameZh");
						provinceNameEn = valueMap.get("provinceNameEn");
						districtNameZh = valueMap.get("districtNameZh");
						districtNameEn = valueMap.get("districtNameEn");
					}
				
					languageCode =rs.getString(7);//en zh
					languageTname = "中文";
					
					String author = null;
					
					String created = rs.getString(8).substring(0, 19) ; 	//爬取时间
					String updated = rs.getString(8).substring(0, 19) ;	//更新时间
					
					boolean isOriginal = true;//是否原创
					
					int view = 0;
					
					int docLength =  titleSrc.length()+textSrc.length() ;
					
					String transFromM = null;
					int pv = 0;
					boolean isHome = true; //是否首页
					boolean isPicture = false;
					
					String comeFrom = "Goonie";
					
					News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId, mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel, countryNameZh, countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn, languageCode, languageTname, author, created, updated, isOriginal, view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom);
					
					newsDao.Insert(news);
					System.out.println ( "crawlerGN" +" "+ totalCount+ " : "+ totalCount +" _"+ mediaNameZh +" - "+ titleSrc);
					news = null;
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					properties .setProperty(key, String.valueOf(totalCount));
					PropertiesUtil.saveProp(properties,filePath);
				}
			}
			
			System.gc();
			rs .close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			page++;
			LOG.info(" insertGN "  + "  page : "+page+"   DONE! totalSize:" + totalCount );
			if(i == 0 ) break;
		}
	}
	
	private class IncreThread extends Thread{
		@Override
		public void run() {
			while ( true ){
				try{
					insertGN();
				}catch(Exception e){
					LOG.error(e);
					e.printStackTrace();
				}finally {
					try {
						Thread.sleep(1000*60*5);
					} catch (Exception e2) {
					}
				}
				
			}
		}
	}
	
	private void start() {
		IncreThread increThread = new IncreThread();
		increThread.start();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		GnInserter ghInserter = new GnInserter() ;
		ghInserter.start();
	}
	
}
