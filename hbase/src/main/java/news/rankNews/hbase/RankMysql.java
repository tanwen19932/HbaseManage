package news.rankNews.hbase;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import news.DicMap;
import news.News;
import news.NewsDao;
import news.hongmaiNews.hbase.CharsetUtil;
import tw.utils.HtmlUtil;
import tw.utils.PropertiesUtil;

public class RankMysql implements Runnable{
	static java.sql.Connection connection;
	protected static String	 JDBC_DRIVER = "com.mysql.jdbc.Driver";
	protected static String	 JDBC_URL = "jdbc:mysql://192.168.55.65:3306/rank1?characterEncoding=utf8";
	protected static String	 JDBC_USER	="zyyt";
	protected static String	 JDBC_PWD	= "Test@123.cn";
	protected static String	 filePath	= RankMysql.class.getResource("/").getPath();
	static Properties properties ;
	static int pageSize ;
	static AtomicInteger page ;
	static String num  ;
	static int startRow ;
	static int totalCount ;
	static int endRow ;
	//25030760
	static {
		Scanner scanner = new Scanner(System.in);
		System.out.println(" input the properties path: ");
		String line;
		while( true ){
			line  = scanner.next();
			if( !line.contains(".") ){
				line = line + ".properties";
			};
			String filePath =  RankMysql.filePath+ line;
			try{
				Class.forName(JDBC_DRIVER);
				connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PWD);
				properties = PropertiesUtil.getProp(filePath);
				RankMysql.filePath= filePath;
				pageSize = 10000;
				page = new AtomicInteger();
				num = (String) properties.get("totalCount");
				totalCount = startRow;
				break;
			}catch(Exception e){
				System.err.println(" Exception! "+e);
				System.out.println(" input the right path: ");
				continue;
			}
		}
	}
	
	public void insertRank() throws ClassNotFoundException, SQLException, IOException {
		/*
		Map<String, String> siteMap = getDicMap("E:\\1402sitesort\\hongmai\\0.txt");
		siteMap.putAll(getDicMap("E:\\1402sitesort\\hongmai\\1.txt"));
		siteMap.putAll(getDicMap("E:\\1402sitesort\\hongmai\\2.txt"));
		*/
		NewsDao newsDao = new NewsDao();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String table = "t_data_p";
		while(true){
			int page = this.page.getAndIncrement();
			String sql = "select a.id,a.page_type , a.ctitle , a.page_time ,a.content,a.site_name ,a.location , a.url ,a.is_important ,a.insert_time from "+
					table+" a where a.id>"+(pageSize*page+startRow)  + "  limit " + 
					 + pageSize ;
			System.out.println(sql);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			int insertCount= 0;
			while(rs.next()){
				totalCount++;
				i++;
				try{
					String id = null; //byte[] id ;
					int mediaType = 1 ;//媒体类型id
					String mediaTname = "新闻";// 新闻
					
					String titleSrc = rs.getString(3);
					String pubdate = sdf.format(new Date( rs.getLong(4)*1000 )); 	//发布时间
					java.sql.Blob blob = rs.getBlob(5);
					String textSrc = new String(blob.getBytes(1, (int) blob.length()));
					
					String websiteId = null;	//站点id
					String mediaNameSrc = rs.getString(6);//数据源名称
					
					String mediaNameZh = rs.getString(6);
					String mediaNameEn = null;
					Integer mediaLevel = 4;
					
					String countryNameZh ="中国";
					String countryNameEn = "China";
					String provinceNameZh = rs.getString("location").replaceAll("[省|特别行政区|市]", "").replaceAll("新疆.*", "新疆");
					String provinceNameEn = DicMap.getCityEn(provinceNameZh);
					String districtNameZh = null;
					String districtNameEn = null;
					
					
					String languageCode =null;//en zh
					String languageTname = null;
					
					
					languageCode = "zh";//en zh
					languageTname = "中文";

					String url = rs.getString(8);
					
					String author = null;
					
					String created =  sdf.format(new Date( rs.getLong("insert_time")*1000 ));; 	//爬取时间
						
					String updated =  created;	//更新时间
					
					boolean isOriginal = true;//是否原创
					
					int view = 0;
					
					int docLength =  titleSrc.length()+textSrc.length() ;
					
					String transFromM = null;
					int pv = 0;
					boolean isHome = rs.getBoolean("is_important"); //是否首页
					boolean isPicture = false;
					
					String comeFrom = "Rank";
					String comeFromDb = rs.getString(1) + table;
					
					News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId,
							mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel.intValue(), countryNameZh,
							countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn,
							languageCode, languageTname, author, created, updated, isOriginal, 
							view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom,comeFromDb ,"");
					
					newsDao.Insert(news);
					news = null;
					System.out.println (  " "+ totalCount+ " : "+ insertCount + mediaNameZh +" - " +languageCode +" "+languageTname+" "+ titleSrc);
					insertCount++;
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					properties .setProperty("totalCount", String.valueOf(totalCount));
					PropertiesUtil.saveProp(properties,filePath);
				}
			}
			if(totalCount>endRow) System.exit(0);
			System.gc();
			rs .close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
//			mysqlHMCon.close();
//			mysqlHMCon = null;
			if(i == 0 ) break;
		}
	}
	public RankMysql() {
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		RankMysql rankMysql = new RankMysql();
		
		int threadNum = 3;
		Thread[] threads = new Thread[threadNum];
		for(int i=0 ; i < threadNum ; i++){
			threads[i] = new Thread(rankMysql);
			threads[i].start();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			insertRank();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
