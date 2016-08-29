package news.tempNews.hbase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.*;
import jxl.read.biff.BiffException;
import news.DicMap;
import news.News;
import news.NewsDao;

public class ExelTemp {
	public static String tryParse(String date) {
		List<String> regexList = new ArrayList<>();
		//By PA Aug 24, 2015
		regexList.add("MMM dd, yyyy");
		regexList.add("MMM dd,HH:mm:ss");
		//Tue, 04/26/2016 - 07:23
		regexList.add("EEEE, MM/dd/yyyy - HH:mm");
		//2016-04-14T12:52:17Z
		regexList.add("yyyy-MM-ddHH:mm:ss");

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i =0 ;i<regexList.size() ; i++){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat(regexList.get(i),Locale.ENGLISH);
				return sdf2.format( sdf.parse(date) );
			}catch (Exception e){
			}
		}
		return null;
	}
	public static void insert() throws BiffException, IOException {
		NewsDao newsDao = new NewsDao();
		WorkbookSettings workbookSettings = new WorkbookSettings();
		workbookSettings.setEncoding("ISO-8859-1"); //解决中文乱码，或GBK
		InputStream stream = new FileInputStream("E:/insert/one belt one road.xlsx");
		Workbook book = Workbook.getWorkbook(stream,workbookSettings);
		Sheet sheet = book.getSheet(0);
		for (int i = 1; i < sheet.getRows(); i++) {
			// 创建一个数组 用来存储每一列的值
			String[] str = new String[8];
			// 列数
			for (int j = 0; j < 8; j++) {
				// 获取第i行，第j列的值
				Cell cell = sheet.getCell(j, i);
				str[j] = cell.getContents().trim() ;
				if(j == 3) str[j] = cell.getContents();
			}
			String id = null; //byte[] id ;
			int mediaType = 1 ;//媒体类型id
			String mediaTname = "新闻";// 新闻
			
			String titleSrc = str[0];
			String pubdate = tryParse(str[1].substring(6)); 	//发布时间
			String textSrc = str[2];
			
			String websiteId = null;	//站点id
			String mediaNameSrc = str[3];//数据源名称
			
			String mediaNameZh = null;
			String mediaNameEn = null;
			Integer mediaLevel =4;
			try {
				mediaLevel = Integer.parseInt(str[4]);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//英文
//			String countryNameZh = DicMap.getCountryZh(str[5]);
//			String countryNameEn = str[5];
			
			//中文
			String countryNameZh = str[5];
			String countryNameEn = DicMap.getCountryEn(str[5]);
			
			String provinceNameZh = null;
			String provinceNameEn = null;
			String districtNameZh = null;
			String districtNameEn = null;
			
			//英文
//			String languageCode = str[6];//en zh
//			String languageTname = DicMap.getLanguageZh(str[6]);
			
			//中文
			String languageCode = DicMap.getLanguageEn(str[6]);//en zh
			String languageTname = str[6];
			
			String url =str[7];
			
			
			String author = null;
			
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
			System.err.println(i);
		}
		book.close();
	}
	
	public static void main(String[] args) throws BiffException, IOException {
		insert();
	}
}
