package news.cisionNews.hbase;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.yarn.webapp.view.Html;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import news.DicMap;
import news.News;
import news.NewsDaoRT;
import tw.utils.FileUtils;
import tw.utils.HtmlUtil;

public class CisionMediaSrc {
	static Set<String> set = new ConcurrentSkipListSet<>();
	static String path = "/home/_hadoop/cisionMediaSrc";
	static {
		try {
			for(String temp : FileUtils.getFileStr(path).split("\r\n")){
				set.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insert(String str) throws Exception {
		Document document = DocumentHelper.parseText(str);
		Element root = document.getRootElement();
		Element GetWISEBulkXMLResult = root.element("GetWISEBulkXMLResult");
		Element ResponseArray = GetWISEBulkXMLResult.element("ResponseArray");
		List<Element> childElements = ResponseArray.elements();
		for (Element child : childElements) {
			try {
				String mediaNameSrc = child.elementText("SiteName");

				String countryNameEn = child.elementText("Location").replaceAll("\n", "");
				// 英文
				String languageCode = DicMap.getLanguageEn(child.elementText("Language"));// en
//				String languageTname = DicMap.getLanguageZh(languageCode);																			// zh

				String url = child.elementText("UrlAddress");
				String domain = HtmlUtil.getDomain(url);
				StringBuilder sb = new StringBuilder();
				sb.append(mediaNameSrc).append("|").append(domain).append("|").append(countryNameEn).append("|").append(languageCode);
				if(set.contains(sb.toString())){
					continue;
				}
				else {
					set.add(sb.toString()); 
					FileUtils.fileAppendStr("/home/_hadoop/cisionMediaSrc", sb.toString());
				}
			} catch (Exception e) {
			}
		}
	}
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		File file = new File("/home/_hadoop/CisionHttp");
		for(File temp :	file.listFiles()){
			try {
				if(!temp.getName().endsWith(".txt")){
					continue;
				}
				System.out.println("+++++++++++++++++++++++++++      "+temp.getName() + "");
				String tempStr = FileUtils.getFileStr(temp.getAbsolutePath());
				executorService.submit( new Runnable() {
					public void run() {
						try {
							insert(tempStr);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
}
