package news.cisionNews.hbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import news.DicMap;
import news.News;
import news.NewsDao;
import tw.utils.PropertiesUtil;

public class XmlTemp {
	private static NewsDao newsDao = new NewsDao();
//	static String rootPath=XmlTemp.class.getResource("/").getPath()+"properties/";
//	static Properties lanPro = null;
//	static
//	{
//		lanPro  = PropertiesUtil.getProp(rootPath + "lan.properties");
//	}
	
	static String getValue(Element root,String tagName){
		return root.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
	}
	
	public static void insert() throws  Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db = dbf.newDocumentBuilder(); 
		Document document = db.parse(new File("E:\\1402sitesort\\a.xml"));  
		Element root = document.getDocumentElement();
		String id = null; //byte[] id ;
		int mediaType = 1 ;//媒体类型id
		String mediaTname = "新闻";// 新闻
		
		String titleSrc = getValue(root ,"Headline");
		String pubdate = getValue(root ,"PublicationDate");	//发布时间
		String textSrc = getValue(root ,"BodyText");
		
		String websiteId =getValue(root ,"ItemID");
		String mediaNameSrc =getValue(root ,"MediaName");
		
		String mediaNameZh =null;
		String mediaNameEn =getValue(root ,"MediaName");
		Integer mediaLevel = Integer.valueOf(getValue(root ,"MediaLevel") );
		
		
		
		//英文
		String countryNameEn = getValue(root, "???");
		String countryNameZh = DicMap.getCountryZh(countryNameEn);
		
		//中文
//		String countryNameZh = getValue(root, "???");
//		String countryNameEn = DicMap.getCountryEn(countryNameZh[5]);
		
		String provinceNameZh = null;
		String provinceNameEn = null;
		String districtNameZh = null;
		String districtNameEn = null;
			
		
		//英文
		String languageCode = getValue(root, "???");//en zh
		String languageTname = DicMap.getCountryZh(countryNameEn);
		
		//中文
//		String languageCode = DicMap.getLanguageEn(str[6]);//en zh
//		String languageTname = str[6];

		String url = getValue(root, "URL");
		
		
		String author = getValue(root, "URL");
		
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
				view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom);
		newsDao.Insert(news);
		
	}
	public static void main(String[] args) throws Exception {
		insert();
	}
}
