package news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import news.hongmaiNews.hbase.CalendarUtil;
import tw.utils.MySQLConnect;
import tw.utils.PropertiesUtil;

public class BhInserter {
	
	private static Log LOG = LogFactory.getLog(BhInserter.class);
	private NewsDao newsDao = new NewsDao();
	private Properties properties  = null;
	private String rootPath=CalendarUtil.class.getResource("/").getPath();
	private String filePath = rootPath+"bh_crawler1.properties";
	
	public void insertBH() throws ClassNotFoundException, SQLException {
		String key = "beihang";
		properties= PropertiesUtil.getProp(filePath);
		int pageSize = 1000;
		int page = 0;
		int startRow  = Integer.parseInt((String) properties.get(key) );
		int totalCount = startRow;
		while(true){
			String sql = "select m.mediaId , m.name, n.title, n.time , n.content , n.websiteId ,s.siteName ,s.weight ,n.downloadtime ,n.url from normalizetext n left join jw_site  s on n.websiteid = s.siteId left join tb_media m on s.infoId = m.mediaId "
					+ "limit " + (pageSize*page+startRow) + "," + pageSize ;
			System.out.println(sql);
			Connection mysqlCon = MySQLConnect.getAM2Connection();
			PreparedStatement preparedStatement = mysqlCon.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while(rs.next()){
				totalCount++;
				i++;
				try{
					String id = null; //byte[] id ;
					
					int mediaType = rs.getInt(1) ;//媒体类型id
					String mediaTname = rs.getString(2);// 新闻
					
					String titleSrc = rs.getString(3);
					String pubdate = rs.getString(4).substring(0, 19); 	//发布时间
					String textSrc = rs.getString(5);
					
					String websiteId = rs.getString(6);	//站点id
					String mediaNameSrc = rs.getString(7);//数据源名称
					
					String mediaNameZh = null;
					String mediaNameEn = null;;
					int mediaLevel =rs.getInt(8); //级别
					String countryNameZh = null;
					String countryNameEn = null;
					String provinceNameZh = null;
					String provinceNameEn = null;
					String districtNameZh = null;
					String districtNameEn = null;
					String languageCode =null;//en zh
					String languageTname = null;
					
					PreparedStatement preparedStatement2 = null;
					ResultSet mediaSrcRs = null;
					Connection mysqlCon5 = MySQLConnect.get5Connection();
					try{
						String siteName = mediaNameSrc.trim();
						for ( int j = 0 ; j < 3 ; j++){
							String sql2 = "select  * from tb_mediasrc where mediaNameZh like ('"+ siteName.trim()+"%');";
							try {
								preparedStatement2 = mysqlCon5.prepareStatement(sql2);
								mediaSrcRs = preparedStatement2.executeQuery();
							} catch (Exception e) {
								e.printStackTrace();
							}
							if(mediaSrcRs.next())
								break;
							if( j==0 ) siteName = siteName.replaceAll("-.*", "");
							if( j==1 ) siteName = siteName.substring(0, siteName.length() -1);
						}
						mediaNameZh = mediaSrcRs.getString(3);
						mediaNameSrc = mediaSrcRs.getString(2);
						mediaNameEn = mediaSrcRs.getString(4);;
						mediaLevel = mediaSrcRs.getInt(5); //级别
						countryNameZh =mediaSrcRs.getString(7);
						countryNameEn = mediaSrcRs.getString(8);
						provinceNameZh = mediaSrcRs.getString(9);
						provinceNameEn = mediaSrcRs.getString(10);
						districtNameZh = mediaSrcRs.getString(11);
						districtNameEn = mediaSrcRs.getString(12);
						
						
						languageCode =mediaSrcRs.getString(14);//en zh
						languageTname = mediaSrcRs.getString(15);
						
					}catch (Exception e){
						System.err.println(mediaNameSrc+"数据源未在数据源库 ！错误！");
						continue;
					}finally {
						if( mediaSrcRs != null ){
							mediaSrcRs.close();
							mediaSrcRs = null;
						}
						if( preparedStatement2 != null ){
							preparedStatement2.close();
							preparedStatement2 = null;
						}
					}
					
		
					
					String author = null;
					
					String created = rs.getString(9).substring(0, 19) ; 	//爬取时间
					String updated = rs.getString(9).substring(0, 19) ;	//更新时间
					
					boolean isOriginal = true;//是否原创
					
					int view = 0;
					String url = rs.getString(10);
					int docLength =  titleSrc.length()+textSrc.length() ;
					
					String transFromM = null;
					int pv = 0;
					boolean isHome = true; //是否首页
					boolean isPicture = false;
					
					String comeFrom = "BeiHang";
					
					News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId, mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel, countryNameZh, countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn, languageCode, languageTname, author, created, updated, isOriginal, view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom);
					
					newsDao.Insert(news);
					System.out.println ( "crawlerBH" +" "+ totalCount+ " : "+ totalCount +" _"+ mediaNameZh +" - "+ titleSrc);
					news = null;
				}catch (Exception e){
					
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
			LOG.info(" insertBH "  + "  page : "+page+"   DONE! totalSize:" + totalCount );
			if(i == 0 ) break;
		}
	}
	
	private class IncreThread extends Thread{
		@Override
		public void run() {
			while ( true ){
				try{
					insertBH();
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
		BhInserter bhInserter = new BhInserter() ;
		bhInserter.start();
	}
	
}
