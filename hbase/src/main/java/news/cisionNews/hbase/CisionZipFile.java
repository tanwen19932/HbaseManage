package news.cisionNews.hbase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONArray;
import org.json.JSONObject;

import news.DicMap;
import news.News;
import news.NewsDao;
import news.NewsDaoTest;
import tw.utils.PropertiesUtil;


public class CisionZipFile {
	static Properties pro = null;
	static String filePath = CisionZipFile.class.getResource("/").getPath()+"cisionZip.properties";
	static AtomicLong totalCount = new AtomicLong();
	static boolean firstCheck = true;
	static ExecutorService executorService = Executors.newFixedThreadPool(10);
//	static String filePath = "E:/properties/cision.properties";
	static{
		pro = new Properties();
		pro = PropertiesUtil.getProp(filePath);
		try{
//			currentFileCount=Long.parseLong(pro.getProperty("currentFileCount","0"));
			totalCount.set(Long.parseLong(pro.getProperty("totalCount")));
		}catch (Exception e){
		}
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
					if(name.endsWith(".zip")){
						if(pro.getProperty(name)==null) {
							File file2= files[i];
							Future<String> future= executorService.submit(new Runnable() {
								public void run() {
									insertZip(file2);
									pro.setProperty(name, "1");
							     	pro.setProperty( "totalCount", String.valueOf(totalCount) );
//							     	currentFileCount=0;
//							     	pro.setProperty( "currentFileCount", String.valueOf(currentFileCount) );
							     	PropertiesUtil.saveProp(pro, filePath);
								}
							} , name);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	private static void insertZip(File file) {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
			insertZipInputStream(zis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insertDir(String dir) {
//		File  file = new File("E:/insert");
		File  file = new File(dir);
		insertDir(file);
		
	}
	
	public static void insertZipInputStream(ZipInputStream zis) throws  Exception{
		NewsDao newsDao = new NewsDao();
		ZipEntry zipEntry;
		while( (zipEntry = zis.getNextEntry())!=null ){
			InputStream is = zis;
			byte[] buf = new byte[2048];
			int read;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			while ((read = is.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			String fileName = zipEntry.getName();
			if( fileName.endsWith(".zip") ){
				insertZipInputStream(new ZipInputStream(new ByteArrayInputStream(bos.toByteArray())));
				continue;
			}
			String currentFileCountStr = pro.getProperty(fileName);
			int currentFileCount =0 ;
			if( currentFileCountStr !=null){
				 currentFileCount = Integer.parseInt(currentFileCountStr);
				 if (currentFileCount==1) {
					 System.out.println(fileName + "  ++++ 已经解压 跳过......   " );
					 continue;
				}
			};
			if(currentFileCount ==0 )
				System.out.println(fileName + "  ++++++++ 开始解压  " );
			String content = new String(bos.toByteArray());
			try {
				JSONObject jo1= null;
				try{
					jo1 = new JSONObject(content).getJSONObject("GetWISEBulkJSONResult");
				}catch (Exception e){
					jo1 = new JSONObject(content).getJSONObject("GetBulkbySTCResult");
				}
				JSONArray ja = jo1.getJSONArray("ResponseArray");
				for(int i = currentFileCount ; i<ja.length() ; i++){
					JSONObject jo = ja.getJSONObject(i);
					String id = null; //byte[] id ;
					int mediaType = 1 ;//媒体类型id
					String mediaTname = "新闻";// 新闻
//					SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy",Locale.ENGLISH);
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String titleSrc = jo.getString("HeadLine");
					String pubdate =  sdf2.format(new Date( Long.parseLong(jo.getString("PublicationDate").replaceAll("[^0-9]", "")) ) );	//发布时间
					String textSrc = jo.getString("BodyText").replaceAll("\\u000a", "##br##").replaceAll("\n", "##br##");
					
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
					totalCount.incrementAndGet();
					currentFileCount++;
					newsDao.Insert(news);
				}
				long tempTotal = totalCount.get() ;
				System.out.println(fileName + "  ++++ 解压完毕   共有 " + (currentFileCount) );
				pro.setProperty( fileName, String.valueOf( 1 ) );
				pro.setProperty( "totalCount", String.valueOf(totalCount.get()) );
				PropertiesUtil.saveProp(pro, filePath);
				System.out.println("insert success ! " + "totalCount:" + tempTotal );
			} catch (Exception e) {
				e.printStackTrace();
			}
			zis.closeEntry();
		}
	}
	public static void main(String[] args) throws Exception {
		insertDir("/home/zyyt/cisionData");
//		insertDir("E:\\insert");
	}
}
