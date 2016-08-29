package news.hongmaiNews.hbase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CalendarUtil {
	static String rootPath=CalendarUtil.class.getResource("/").getPath();
	static String filePath = rootPath+"hm_crawler.properties";
	static Properties properties = null;
	private static Log LOG = LogFactory.getLog(CalendarUtil.class);
	static {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			properties = new Properties();
			properties.load(fis);            //从输入流中读取属性文件的内容
			fis.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Properties getHmProp() throws IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Properties properties = new Properties();
		
		Date endDate = sdf.parse("20160101");
		Date beginDate = sdf.parse("20161231");
		while( Integer.parseInt( sdf.format(beginDate) ) - Integer.parseInt( sdf.format(endDate) ) > 0 ){
			String database  = "articles_"+sdf.format(beginDate.getTime()).substring(4);
			properties.setProperty(database, "0");
			beginDate = new Date( beginDate.getTime() - 1000*60*60*24);
		}
		System.out.println(properties.size());
		OutputStream fos = new FileOutputStream(filePath);  
		properties.store(fos,"");  
		fos.close();
//		saveProp(properties);
		return properties;
	}
	
	public static Properties getPro() {
		if(properties != null){
			return properties;
		}
		return null;
	}
	
	public static synchronized void saveProp( Properties properties ) {
		while(true){
			try {
				FileOutputStream fos = new FileOutputStream(filePath);
				properties.store(fos,"???");  
				fos.close();
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}  
		}
	}
	
	public static void days() throws ParseException  {
		String date1="1987-01-01";
		String date2="2010-01-01";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d1=sdf.parse(date1);
		Date d2=sdf.parse(date2);
		long daysBetween=(d2.getTime()-d1.getTime())/(3600*24*1000);
		System.out.println("1987-01-01 与 2010-01-01 相隔 "+daysBetween+" 天");
	}
	
	public static void main(String[] args) throws IOException {
		
		try {
//			List<String> tables = getHmDatabases();
//			while( !tables.isEmpty() ){
//				String table = tables.remove(0);
//				System.out.println(table);
//			}
			getHmProp();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
