package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.tw.common.FileUtils;
import edu.buaa.nlp.tw.common.StringUtil;
import handler.Handler;
import multi.patt.match.ac.AhoCorasick;
import multi.patt.match.ac.SearchResult;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class NewsSensitiveHandler implements Handler<ProcessedNews> {
	private static Logger LOG = Logger.getLogger(NewsSensitiveHandler.class);
	static String websiteIdSensifilePath = "data/sensitive/websiteIdSensi.txt";
	static String websiteIdProblemfilePath = "data/sensitive/websiteIdProblem.txt";
	static long lastSensiFileLength = 0;
	static long lastProblemFileLength = 0;
	private static Set<String> websiteIdSensi = new ConcurrentSkipListSet<>();
	private static Set<String> websiteIdProblem = new ConcurrentSkipListSet<>();
	private static Map<String, String> hashWebSiteNames = null; // websiteName
	private static Map<String, String> hashWebDomains = null; // websiteDomain
	private static Map<String, String> hashSentiWords = null; // 敏感词
	private static AhoCorasick tree = null;
	
	static {
		loadModels();
	}
	@Override
	public boolean handle(ProcessedNews news) {
		handleSensitive(news);
		LOG.debug(" 新闻:  " + news.getId() + "敏感：" + news.getSensitiveType() + news.getSensitiveCls());
		return !isDump(news);
	}
	
	private boolean isDump(ProcessedNews news) {
		return (news.getSensitiveType()==-1);
	}

	public static boolean loadModels() {
		try {
			hashWebSiteNames = new HashMap<String, String>();
			hashSentiWords = new HashMap<String, String>();
			hashWebDomains = new HashMap<String, String>();

			InputStreamReader isR = null;
			BufferedReader br = null;
			String line;

			isR = new InputStreamReader(new FileInputStream("data/sensitive/words3.txt"), "GB18030");
			br = new BufferedReader(isR);
			tree = new AhoCorasick();
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				String[] ids = line.split("\\t");
				// ids[0] = ids[0].trim();
				if (ids[0].length() < 2) {
					continue;
				}
				if (ids.length >= 2) {

					hashSentiWords.put(ids[0], ids[1].trim());
				} else {
					hashSentiWords.put(ids[0], "");
				}
				tree.add(ids[0].trim().getBytes(), ids[0].trim());
			}
			tree.prepare();
			br.close();
			isR.close();

			isR = new InputStreamReader(new FileInputStream("data/sensitive/hosts.txt"), "GB18030");
			br = new BufferedReader(isR);
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				String[] ids = line.split("\\t");
				if (ids.length >= 2) {
					hashWebSiteNames.put(ids[0].trim(), ids[1].trim());
					hashWebDomains.put(ids[1].trim(), ids[0].trim());
				}
			}
			br.close();
			isR.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void handleSensitive(ProcessedNews news) {
		String url = news.getUrl();
		String src = news.getTextSrc();
		int isSensitive = 0;
		int sensitiveType = 0;
		String sensitiveCls = null;
		String websiteId= "";
		if( !StringUtil.isNull(news.getWebsiteId() ) ){
			websiteId= news.getWebsiteId();
		}
		if(websiteIdProblem.contains(websiteId)){
			news.setSensitive(1);
			news.setSensitiveType(-1);
			news.setSensitiveCls("问题乱码站点");
			return;
		}
		if(websiteIdSensi.contains(websiteId)){
			news.setSensitive(1);
			news.setSensitiveType(10);
			news.setSensitiveCls("红麦敏感网站");
			return;
		}
		if (	hashWebSiteNames.containsKey(news.getMediaNameSrc().trim())
				|| hashWebDomains.containsKey(news.getMediaNameSrc().trim())
				) {
			news.setSensitive(1);
			news.setSensitiveType(30);
			news.setSensitiveCls("墙网站");
			return;
		} else {
			for (String key : hashWebDomains.keySet()) {
				if (url.indexOf(key) >= 0) {
					news.setSensitive(1);
					news.setSensitiveType(30);
					news.setSensitiveCls("墙网站");
					return;
				}
			}
			if (isSensitive == 0) {
				Iterator iter = tree.search(src.getBytes());
				SearchResult result = null;
				if (iter.hasNext()) {
					result = (SearchResult) iter.next();
					isSensitive = 1;
					sensitiveType = 20;
					sensitiveCls = hashSentiWords.get(result.getOutputs().iterator().next().toString());
				}
			}
		}
		news.setSensitive(isSensitive);
		news.setSensitiveType(sensitiveType);
		news.setSensitiveCls(sensitiveCls);
		return;
	}

	static {
		Thread updateThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						if (isFileChange(websiteIdSensifilePath , lastSensiFileLength)) {
							getSensitiveWebsiteId(websiteIdSensifilePath,websiteIdSensi);
						}
						if (isFileChange(websiteIdProblemfilePath , lastProblemFileLength)) {
							getSensitiveWebsiteId(websiteIdProblemfilePath,websiteIdProblem);
						}
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		updateThread.start();
	}


	private static String getSensitiveWebsiteId(String path , Set<String> set) {
		StringBuilder result = new StringBuilder();
		try {
			String raw = FileUtils.getFileStr(path);
			for (String id : raw.split(",")) {
				if (id.trim().equals(""))
					continue;
				if (!set.contains(id)) {
					set.add(id);
					result.append(id).append(",");
				}
			}
			result.replace(result.length() - 1, result.length(), "");
			LOG.info("读取到新的WebSiteID  当前列表个数：" + set.size());
			FileUtils.writeFile(path, result.toString(), "utf-8");
		} catch (Exception e) {
		}
		return result.toString();
	}

	private static boolean isFileChange(String filePath ,Long lastLenth) {
		boolean isChanged = false;
		File temp = new File(filePath);
		LOG.debug("检测敏感文件websiteid.txt " + temp.getAbsolutePath() + " 是否改变  原来大小：" + lastLenth + "当前大小：" + temp.length());
		if (temp.length() != lastLenth) {
			lastLenth = temp.length();
			LOG.info("  检测 敏感文件 " +temp.getAbsolutePath() + " 改变 ！");
			isChanged = true;
		}
		return isChanged;
	}
}