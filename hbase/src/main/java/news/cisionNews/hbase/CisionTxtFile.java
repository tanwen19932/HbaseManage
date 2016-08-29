package news.cisionNews.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import news.DicMap;
import news.News;
import news.NewsDao;
import tw.utils.PropertiesUtil;


public class CisionTxtFile {
	static Properties pro = null;
	static String filePath = CisionTxtFile.class.getResource("/").getPath()+"cision.properties";;
//	static String filePath = "E:/properties/cision.properties";
	static{
		pro = new Properties();
		pro = PropertiesUtil.getProp(filePath);
	}
	public static void insertDir(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for( int i = 0 ; i < files.length; i++){
				try {
					String name = files[i].getName();
					if(files[i].isDirectory()){
						insertDir(files[i]);
					}
					else if(name.endsWith(".zip")) continue;
					else if(pro.getProperty(name)==null) {
				     	insert(files[i]);
				     	pro.setProperty(name, "1");
				     	PropertiesUtil.saveProp(pro, filePath);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void insertDir(String dir) {
//		File  file = new File("E:/insert");
		File  file = new File(dir);
		insertDir(file);
		
	}
	
	
	
	public static void insert(File file) throws  Exception{
		NewsDao newsDao =new NewsDao();
		BufferedReader bf = new BufferedReader(new FileReader(file));
		String line ;
		StringBuffer sb = new StringBuffer() ;
		while( (line = bf.readLine()) != null){
			sb.append(line);
		}
		try {
			JSONObject jo1 = new JSONObject(sb.toString()).getJSONObject("GetBulkbySTCResult");
			JSONArray ja = jo1.getJSONArray("ResponseArray");
			
			
			for(int i = 0 ; i<ja.length() ; i++){
				JSONObject jo = ja.getJSONObject(i);
				String id = null; //byte[] id ;
				int mediaType = 1 ;//媒体类型id
				String mediaTname = "新闻";// 新闻
//				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy",Locale.ENGLISH);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				String titleSrc = jo.getString("HeadLine");
				String pubdate =  sdf2.format(new Date( Long.parseLong(jo.getString("PublicationDate").replaceAll("[^0-9]", "")) ) );	//发布时间
				String textSrc = jo.getString("BodyText").replaceAll("\\u000a", "##br##");
				
				String websiteId =null;
				String mediaNameSrc =jo.getString("SiteName");
				
				String mediaNameZh =null;
				String mediaNameEn = null;
				Integer mediaLevel = 4;
				String countryNameEn = jo.getString("Location");
				String countryNameZh =DicMap.getCountryZh(countryNameEn);
				String provinceNameZh =null;
				String provinceNameEn =null;
				String districtNameZh =null;
				String districtNameEn =null;
				
				
				String languageCode = DicMap.getLanguageEn(jo.getString("Language"));//en zh
				String languageTname = DicMap.getLanguageZh(languageCode);

				String url =jo.getString("UrlAddress");
				
				
				String author =null;
				
				String created =  pubdate; 	//爬取时间
				
				String updated =  pubdate;	//更新时间
				
				boolean isOriginal = true;//是否原创
				
				int view = 0;
				
				int docLength =  titleSrc.length()+textSrc.length() ;
				
				String transFromM = null;
				int pv = 0;
				boolean isHome = true; //是否首页
				boolean isPicture = false;
				
				String comeFrom = "Cision";
				News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId,
						mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel.intValue(), countryNameZh,
						countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn,
						languageCode, languageTname, author, created, updated, isOriginal, 
						view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom ,null,null);
				news.setComeFromDb("CisionTxt");
				newsDao.Insert(news);
				System.out.println("insert success !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(file.getName());
		}
		
	}
	public static void main(String[] args) throws Exception {
		insertDir("/home/zyyt/db");
	}
}
