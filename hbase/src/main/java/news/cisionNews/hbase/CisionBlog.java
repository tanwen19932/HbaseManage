package news.cisionNews.hbase;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;

import news.DicMap;
import news.News;
import news.NewsDaoRT;
public class CisionBlog extends CisionAPI{
	
	@Override
	public boolean insert(Document document) throws Exception {
		NewsDaoRT newsDao = new NewsDaoRT();
		Element root = document.getRootElement();
		Element GetWISEBulkXMLResult = root.element("GetBlogsOngoingXMLResult");
		Element ResponseArray = GetWISEBulkXMLResult.element("ResponseArray");
		List<Element> childElements = ResponseArray.elements();
		int insertCount = 0;
		for (Element child : childElements) {
			try {
				String id = null; // byte[] id ;
				int mediaType = 8;// 媒体类型id
				String mediaTname = "博客";// 新闻

				String titleSrc = child.elementText("HeadLine");
				String pubdate = child.elementText("PublicationDate").replaceAll("T", " ").replaceAll("Z", ""); // 发布时间
				String textSrc = child.elementText("BodyText").replaceAll("\\u000a", "##br##");
				;

				String websiteId = child.elementText("");
				String mediaNameSrc = child.elementText("SiteName");

				String mediaNameZh = null;
				String mediaNameEn = child.elementText("SiteName");
				Integer mediaLevel = 4;

				// 英文
				String countryNameEn = child.elementText("Location").replaceAll("\n", "");
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
				String languageCode = child.elementText("Language");// en
																	// zh
				String languageTname = DicMap.getLanguageZh(languageCode);

				// 中文
				// String languageCode = DicMap.getLanguageEn(str[6]);//en
				// zh
				// String languageTname = str[6];

				String url = child.elementText("UrlAddress");

				String author = child.elementText("AuthorName");

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
				news.setComeFromDb("CisionBlog");
				insertCount++;
				
				newsDao.Insert(news);
				news = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		TOTALCOUNT += insertCount;
		System.out.println("\n 总共数量" +TOTALCOUNT + " 文件插入数：" + insertCount);
		System.out.println(" 今日总量："+ todayCount.addAndGet(insertCount));
		if(insertCount == 0){
			Element Message = GetWISEBulkXMLResult.element("Message");
			if(Message.getText().equals("Daily articles limit exceded.")){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		CisionBlog CisionBlog = new CisionBlog();
		CisionBlog.setDayLimit(700000);
		CisionBlog.requestUrlPrefix = "http://195.23.32.196/wiseapibulk/rest.svc/blogsongoing/XML/2d488b2d-6af3-4992-b310-d7a7788c600b?since=TIME&interval=1";
		CisionBlog.resultBulkXML = "GetBlogsOngoingXMLResult";
		CisionBlog.interval = 3;
		CisionBlog.start();
	}
}
