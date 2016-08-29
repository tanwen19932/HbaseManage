package news.tempNews.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import news.DicMap;
import news.News;
import news.NewsDao;


public class JsonTemp {
	public static void insert(String filePath) throws  Exception{
		NewsDao newsDao =new NewsDao();
		File file  = new File(filePath);
		BufferedReader bf = new BufferedReader(new FileReader(file));
		String line ;
		while( (line = bf.readLine()) != null){
			JSONObject jo = new JSONObject(line);
			String id = null; //byte[] id ;
			int mediaType = 1 ;//媒体类型id
			String mediaTname = "新闻";// 新闻
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy",Locale.ENGLISH);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String titleSrc = jo.getString("title");
			String pubdate =  sdf2.format(sdf.parse(jo.getString("publishTime")));	//发布时间
			String textSrc = jo.getString("contents");
			
			String websiteId =null;
			String mediaNameSrc =jo.getString("site");
			
			String mediaNameZh =null;
			String mediaNameEn = null;
			Integer mediaLevel = 4;
			String countryNameZh =DicMap.getCountryZhByAbbr(jo.getString("nation"));
			String countryNameEn =DicMap.getCountryEnByAbbr(jo.getString("nation"));;
			String provinceNameZh =null;
			String provinceNameEn =null;
			String districtNameZh =null;
			String districtNameEn =null;
			
			
			String languageCode = jo.getString("language");//en zh
			String languageTname = DicMap.getLanguageZh(languageCode);

			String url =jo.getString("url");
			
			
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
			newsDao.Insert(news);
			System.out.println("insert success !");
		}
	
	}
	public static void main(String[] args) throws Exception {
		System.out.println("请输入Path:");
		Scanner s = new Scanner(System.in);
		while (true) {
			String filePath = s.nextLine();
			if (!filePath.equals("Q!"))
				insert(filePath);
			else
				return;
		}
		
		
	}
}
