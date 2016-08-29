package news.cisionNews.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import news.DicMap;
import news.News;
import news.NewsDaoRT;
import tw.utils.FileUtils;
import tw.utils.HttpUtil;
import tw.utils.PropertiesUtil;

public class CisionAPI {
	protected static Log LOG;
	protected static String lastDateStr = null;
	protected static Properties properties = null;
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	// static String proPath = "E:/properties/CisionHttp.properties";
	protected static long TOTALCOUNT = 0;
	protected static String proPath = CisionAPI.class.getResource("/").getPath() + "CisionHttp.properties";
	protected static TreeSet<String> doingTask;
	protected static String CisionAPIDir = CisionAPI.class.getResource("/").getPath() + "/";
	protected static AtomicInteger todayCount = new AtomicInteger();
	protected int threads;
	protected String requestUrlPrefix = "http://195.23.32.196/wiseapibulk/rest.svc/bulk/xml/13b860b0-7c69-47fa-8a9f-482d638c32e0?since=TIME&interval=1";
	protected String resultBulkXML = "GetWISEBulkXMLResult";
	protected long interval = 1;
	ExecutorService executorService;

	protected static int dayLimit = Integer.MAX_VALUE;

	public void setDayLimit(int dayLimit) {
		CisionAPI.dayLimit = dayLimit;
	}

	public CisionAPI() {
		LOG = LogFactory.getLog(this.getClass());
		try {
			proPath = this.getClass().getResource("/").getPath() + this.getClass().getSimpleName() + ".properties";
			File cisionHttpDirFile = new File(CisionAPIDir);
			if (!cisionHttpDirFile.exists())
				cisionHttpDirFile.mkdirs();
			properties = PropertiesUtil.getProp(proPath);
			lastDateStr = properties.getProperty("since");
			if (lastDateStr == null) {
				System.out.println("lastDateStr is null!!");
				System.exit(0);
			}
			TOTALCOUNT = Integer.parseInt(properties.getProperty("totalCount"));
			String json = properties.getProperty("undoTasks");
			if (json == null)
				json = "";
			String[] jsonStr = json.split("[\\[|, |\\]]");
			doingTask = new TreeSet<String>();

			for (int i = 0; i < jsonStr.length; i++) {
				if (!jsonStr[i].equals(""))
					doingTask.add(jsonStr[i]);
			}
			System.out.println("上次运行后未完成的任务共有：" + doingTask.size());
			Scanner scanner = new Scanner(System.in);
			System.out.println(" input the THREADS you want: ");
			String line;
			while (true) {
				line = scanner.next();
				try {
					threads = Integer.parseInt(line);
					break;
				} catch (Exception e) {
					System.err.println(" Exception! " + e);
					System.out.println(" input the right num: ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	static List<String> taskList = Collections.synchronizedList(new ArrayList<>());

	public synchronized String taskStart(List<String> taskList) throws Exception {
		if (!taskList.isEmpty()) {
			String task = taskList.remove(taskList.size() - 1);
			return task;
		}
		return null;
	}

	public void runByThread() {
		int retryTimes = 0;
		boolean retry = false;
		String task = null;
		File file = null;
		while (true) {
			try {
				if (!retry || task == null)
					task = taskStart(taskList);
				else if (retryTimes >= 2 && task != null) {
					deleteUndoTasks(task);
					retryTimes = 0;
					retry = false;
					task = taskStart(taskList);
				}
				if (task == null) {
					Thread.sleep(3000);
					continue;
				}

				file = new File(CisionAPIDir + task.replaceAll("[:]", "_") + ".txt");
				if (retryTimes == 0 && file.exists()) {
					deleteUndoTasks(task);
					retry = false;
					retryTimes = 0;
				}
				System.out.println("开始任务：" + task);
				
//				String content = getByWget(task);
				String content = getByHttp(task);
				
				if (!insert(content)) {
					retryTimes++;
					retry = true;
					LOG.error(task + " 达到每日限制 !   " + retryTimes);
					deleteUndoTasks(task);
					file.delete();
					Thread.sleep(1000 * 60 * 60);
				} else {
					deleteUndoTasks(task);
					retry = false;
					retryTimes = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				retry = true;
				retryTimes++;
				LOG.error(task + "任务失败 重试" + retryTimes);
				continue;
			}
		}
	}

	private String getByHttp(String task) throws IOException {
		String requestUrl = requestUrlPrefix.replaceAll("TIME", task);
		
//		String content = HttpUtil.getHttpContent("http://195.23.32.196/wiseapibulk/rest.svc/socialmedia/XML/4c7e54f6-57a5-43d6-9528-b6ad98d1ed52?index=1000");
//		String content = "aa";
		String content = HttpUtil.getHttpContent( requestUrl );
		if( content != null||!content.equals("")){
			File file = new File(CisionAPIDir + task.replaceAll("[:]", "_") + ".txt");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		}
		
		return content;
	}

	private String getByWget(String task) throws IOException, InterruptedException {
		String requestUrl = requestUrlPrefix.replaceAll("TIME", task);
		File file = new File(CisionAPIDir + task.replaceAll("[:]", "_") + ".txt");
		String command = "wget " + " -O " + file.getName() + "  " + requestUrl;
		Process pro = Runtime.getRuntime().exec(command);
		LOG.info("执行命令..." + command);
		LOG.info("正在下载..." + task);
		pro.waitFor();
		LOG.info("下载成功..." + task);
		BufferedReader read = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
		String line = null;
		while ((line = read.readLine()) != null) {
			System.out.println("??" + line);
		}
		while (true) {
			if (pro.isAlive()) {
				System.out.println("等待下载....");
				wait(5000);
			} else
				break;
		}
		String content = FileUtils.getFileStr(file.getAbsolutePath());
		return content;
	}

	public int check(String src) throws Exception {
		int i = 0;
		try {
			Document document = DocumentHelper.parseText(src);
			Element root = document.getRootElement();
			Element GetWISEBulkXMLResult = root.element(resultBulkXML);
			Element ResponseArray = GetWISEBulkXMLResult.element("ResponseArray");
			List<Element> childElements = ResponseArray.elements();
			for (Element child : childElements) {
				i++;
			}
			return i;

		} catch (Exception e) {
			return 0;
		}
	}

	public boolean insert(String httpUrl) throws Exception {
		// SAXReader reader = new SAXReader();
		// Document document = reader.read(httpUrl);
		Document document = DocumentHelper.parseText(httpUrl);
		return insert(document);
	}

	public boolean insert(Document document) throws Exception {
		NewsDaoRT newsDao = new NewsDaoRT();
		Element root = document.getRootElement();
		Element GetWISEBulkXMLResult = root.element(resultBulkXML);
		Element ResponseArray = GetWISEBulkXMLResult.element("ResponseArray");
		List<Element> childElements = ResponseArray.elements();
		int insertCount = 0;
		for (Element child : childElements) {
			try {
				String id = null; // byte[] id ;
				int mediaType = 1;// 媒体类型id
				String mediaTname = "新闻";// 新闻

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
				String languageCode = DicMap.getLanguageEn(child.elementText("Language"));// en
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
				news.setComeFromDb("CisionHttp");
				TOTALCOUNT++;
				insertCount++;
				newsDao.Insert(news);
				news = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		properties.setProperty("totalCount", String.valueOf(TOTALCOUNT));
		System.out.println("\n\n\n总数：" + TOTALCOUNT + " 文件插入数：" + insertCount);
		System.out.println("今日总数：" + todayCount.addAndGet(insertCount));
		if (insertCount == 0) {
			Element Message = GetWISEBulkXMLResult.element("Message");
			if (Message.getText().equals("Daily articles limit exceded.")) {
				return false;
			}

		}
		return true;
	}

	public synchronized void deleteUndoTasks(String task) {
		if (doingTask.remove(task)) {
			LOG.info("完成任务：" + task);
			properties.setProperty("undoTasks", doingTask.toString());
			PropertiesUtil.saveProp(properties, proPath);
		}
	}

	public void start() {
		
		long nowTime = System.currentTimeMillis();
		Date lastDate = null;
		try {
			for (Iterator<String> iter = doingTask.iterator(); iter.hasNext();) {
				String task = (String) iter.next();
				taskList.add(task);
				System.out.println("加入任务：" + task + "任务数量 " + taskList.size());
			}
		} catch (Exception e) {
		}
		executorService = Executors.newFixedThreadPool(threads);
		for (int i = 0; i < threads; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					runByThread();
				}
			});
		}
		System.out.println("当前线程数："+threads);
		System.out.println("当前获取间隔："+interval);
		
		nowTime = System.currentTimeMillis() - 10 * 60 * 60 * 1000;
		String today = sdf.format(nowTime);
		do {
			try {
				lastDate = sdf.parse(lastDateStr);
				nowTime = System.currentTimeMillis() - 10 * 60 * 60 * 1000;
				long interval = nowTime - lastDate.getTime();
				while (interval < (1000 * 60 * this.interval) ) {
					Thread.sleep(30 * 1000);
					interval = System.currentTimeMillis() - lastDate.getTime() - 10 * 60 * 60 * 1000;
				}

				String nowTask = sdf.format(new Date(lastDate.getTime() + 1000 * 61));
				if (!nowTask.startsWith(today.substring(0, 10))) {
					LOG.info("结束 "+ today.substring(0, 10) +" 共插入： " + todayCount.get());
					todayCount.set(0);
					today = nowTask;
				}
				if ( todayCount.get() >= dayLimit ) {
					System.out.println(lastDateStr +" 达到每日限制： " + todayCount.get());
					lastDateStr = nowTask;
					doingTask.clear();
					taskList.clear();
					continue;
				}
				taskList.add(nowTask);
				// System.out.println(taskList.pop()+taskList.size());
				System.out.println("加入任务：" + nowTask + "当前任务个数" + taskList.size());
				doingTask.add(nowTask);

				properties.setProperty("undoTasks", doingTask.toString());
				properties.setProperty("since", nowTask);
				PropertiesUtil.saveProp(properties, proPath);
				lastDateStr = nowTask;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (true);

	}

	public synchronized int getTaskSize() {
		return taskList.size();
	}
}
