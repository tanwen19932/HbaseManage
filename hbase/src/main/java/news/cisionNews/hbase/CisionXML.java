package news.cisionNews.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import news.DicMap;
import news.News;
import news.NewsDao;
import tw.utils.PropertiesUtil;

public class CisionXML {
	static Properties properties = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	// static String proPath = "E:/properties/CisionHttp.properties";
	static int TOTALCOUNT = 0;
	static String proPath = CisionXML.class.getResource("/").getPath() + "CisionXML.properties";
	static {
		properties = PropertiesUtil.getProp(proPath);
		try{
			TOTALCOUNT = Integer.parseInt(properties.getProperty("totalCount"));
		}catch(Exception e){
			TOTALCOUNT = 0;
		}
	}
	
	
	public static void insertDir() {
		File file = new File("E:/insert");
//		File  file = new File("/home/cision/cision_data");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				try {
					String name = files[i].getName();
					if(name.endsWith(".xml"))
						insert(files[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void insert(File file) throws Exception {
		BufferedReader bf = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuffer sb = new StringBuffer();
		boolean start = false;
		boolean end = false;
		line = bf.readLine();
		for(int i = 0 ; i< TOTALCOUNT ; ){
			int beginIndex ;
			while( (beginIndex = line.indexOf("<b:anyType"))!= -1 ){
				line = line.substring(beginIndex+1);
				i++;
			};
			line= bf.readLine();
		}
		while ( line!=null ) {
			if (!start) {
				sb.delete(0, sb.length());
				if(line.contains("<b:anyType")){
					start = true;
					int beginIndex = line.indexOf("<b:anyType");
					sb.append( line.substring(beginIndex).replaceAll(" i:type=\"a:BulkDoc\"", "") );
					if (line.contains("</b:anyType>")) {
						int endIndex = line.indexOf("</b:anyType>");
						if(endIndex > beginIndex) {
							sb.append(line.substring( beginIndex, + 12));
							end = true;
						}
					}
				}
				
			} else if (line.contains("</b:anyType>")) {
				sb.append(line.substring(0,line.indexOf("</b:anyType>") + 12));
				end = true;
			}else {
				sb.append(line);
			}
			if (end) {
				insert(sb.toString());
				start = false;
				end = false;
				continue;
			}
			sb.append("<br/>");
			line = bf.readLine();
		}

	}

	public static void insert(String xml) throws DocumentException {
//		NewsDao newsDao = new NewsDao();
		String regex = "(?<=</?)\\w:(?=(.*?))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(xml);
		System.out.println(matcher.find());
		String xml2 = matcher.replaceAll("");
		Document document = DocumentHelper.parseText(xml2);
		
		Element root = document.getRootElement();
		System.out.println( document.asXML() );
		
		try {

			String id = null; // byte[] id ;
			int mediaType = 1;// 媒体类型id
			String mediaTname = "新闻";// 新闻

			String titleSrc = root.elementText("HeadLine");
			String pubdate = root.elementText("PublicationDate").replaceAll("T", " ").replaceAll("Z", ""); // 发布时间
			String textSrc = root.elementText("BodyText");

			String websiteId = null;
			String mediaNameSrc = root.elementText("SiteName");

			String mediaNameZh = null;
			String mediaNameEn = root.elementText("SiteName");
			Integer mediaLevel = 4;

			// 英文
			String countryNameEn = root.elementText("Location").replaceAll("\n", "");
			String countryNameZh = DicMap.getCountryZh(countryNameEn);

			// 中文
			// String countryNameZh = child.elementText("???");
			// String countryNameEn =
			// DicMap.getCountryEn(countryNameZh[5]);

			String provinceNameZh = null;
			String provinceNameEn = null;
			String districtNameZh = null;
			String districtNameEn = null;

			// 英文
			String languageCode = DicMap.getLanguageEn(root.elementText("Language"));// en
																						// zh
			String languageTname = DicMap.getLanguageZh(languageCode);

			// 中文
			// String languageCode = DicMap.getLanguageEn(str[6]);//en
			// zh
			// String languageTname = str[6];

			String url = root.elementText("UrlAddress");

			String author = root.elementText("AuthorName");

			String created = pubdate; // 爬取时间

			String updated = pubdate; // 更新时间

			boolean isOriginal = true;// 是否原创

			int view = 0;

			int docLength = titleSrc.length() + textSrc.length();

			String transFromM = null;
			int pv = 0;
			boolean isHome = true; // 是否首页
			boolean isPicture = false;

			String comeFrom = "Cision";
			News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId, mediaNameSrc,
					mediaNameZh, mediaNameEn, mediaLevel.intValue(), countryNameZh, countryNameEn, provinceNameZh,
					provinceNameEn, districtNameZh, districtNameEn, languageCode, languageTname, author, created,
					updated, isOriginal, view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom);
			news.setComeFromDb("CisionXML");
			System.out.println(TOTALCOUNT++);
			properties.setProperty( "totalCount", String.valueOf(TOTALCOUNT) );
			PropertiesUtil.saveProp(properties, proPath);
//			newsDao.Insert(news);
			news = null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		insertDir();
	}
}
