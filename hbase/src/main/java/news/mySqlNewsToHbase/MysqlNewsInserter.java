package news.mySqlNewsToHbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import news.News;
import news.NewsDao;
import news.mediaSrc.util.MediaSrcUtil;
import tw.utils.PropertiesUtil;

public abstract class MysqlNewsInserter implements Runnable{
	
	private static Log LOG ;
	protected static String rootPath=MysqlNewsInserter.class.getResource("/").getPath();
	
	protected static String propertiesPath ;
	protected static Connection mysqlCon =null;
	protected static int pageSize ;
	protected AtomicInteger page ;
	protected static String num  ;
	protected static long startRow ;
	protected static long totalCount ;
	protected static long endRow ;
	
	private static Properties properties ;
	protected static Map<String, Map<String,String> > mediaSrcMap ;
	
	public MysqlNewsInserter() {
		LOG = LogFactory.getLog( this.getClass() );
		Scanner scanner = new Scanner(System.in);
		System.out.println(" input the properties path: ");
		String line;
		while( true ){
			line  = scanner.next();
			if( !line.contains(".") ){
				line = line + ".properties";
			};
			propertiesPath = rootPath + line;
			try{
				properties = PropertiesUtil.getProp(propertiesPath);
				Class.forName(properties.getProperty("JDBC_DRIVER"));
				mysqlCon = DriverManager.getConnection( properties.getProperty("JDBC_URL"),properties.getProperty("JDBC_USER"),properties.getProperty("JDBC_PWD") );
				mediaSrcMap = MediaSrcUtil.getInstance();
				pageSize = 10000;
				page = new AtomicInteger();
				num = (String) properties.get("totalCount");
				startRow = Long.parseLong(num); 
				totalCount = startRow;
			}catch(Exception e){
				System.err.println(" Exception! "+e);
				System.out.println(" input the right path: ");
				continue;
			}
		}
	}
	
	@Override 
	public void run() {
		try {
			insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public void start( int threadNum )  {
		for (int i = 0; i < threadNum; i++) {
			Thread thread = new Thread(this);
			thread.start();
		}
	}
	
	
	
	public void insert() throws SQLException   {
		NewsDao newsDao = new NewsDao();
		while(true){
			int page = this.page.getAndIncrement();
			String sql = generateSql( page);
			System.out.println(sql);
			
			PreparedStatement preparedStatement = mysqlCon.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			int insertCount= 0;
			while(rs.next()){
				totalCount++;
				i++;
				try{
					News news = getNewsFromRS(rs);
					newsDao.Insert(news);
					System.out.println (  " "+ totalCount+ " : "+ insertCount + news.getMediaNameZh() +" - " +news.getLanguageCode() +" "+news.getLanguageTname()+" "+ news.getTitleSrc());
					news = null;
					insertCount++;
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					LOG.info(" ");
					properties .setProperty("totalCount", String.valueOf(totalCount));
					PropertiesUtil.saveProp(properties,propertiesPath);
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

	public abstract String generateSql(int page) ;

	public abstract News getNewsFromRS(ResultSet rs) throws SQLException;
	
}
