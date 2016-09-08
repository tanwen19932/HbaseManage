package news.cisionNews.hbase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.thrift2.generated.THBaseService.Processor.put;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hbase.util.HbaseUtil;
import news.DicMap;
import news.News;
import news.NewsDao;
import news.NewsDaoTest;
import news.NewsUtil;
import tw.utils.PropertiesUtil;

public class CisionZipFileV2 {
	static Properties pro = null;
	private static final Logger LOG = LoggerFactory.getLogger(CisionZipFileV2.class);
	static String filePath = CisionZipFileV2.class.getResource("/").getPath() + "cisionZip.properties";
	static AtomicLong totalCount = new AtomicLong();
	static boolean firstCheck = true;
	private static Connection conn = null;
	private static TableName tableName = TableName.valueOf("NewsArticleBE2");
	static ExecutorService executorService;
	// static String filePath = "E:/properties/cision.properties";
	static {
		pro = new Properties();
		pro = PropertiesUtil.getProp(filePath);
		Scanner scanner = new Scanner(System.in);
		System.out.println(" input the THREADS you want: ");
		String line;
		int threads;
		while (true) {
			line = scanner.next();
			try {
				threads = Integer.parseInt(line);
				executorService = Executors.newFixedThreadPool(threads);
				break;
			} catch (Exception e) {
				System.err.println(" Exception! " + e);
				System.out.println(" input the right num: ");
			}
		}
		try {
			conn = ConnectionFactory.createConnection(HbaseUtil.getConf());
			// currentFileCount=Long.parseLong(pro.getProperty("currentFileCount","0"));
			totalCount.set(Long.parseLong(pro.getProperty("totalCount")));
		} catch (Exception e) {
		}
	}

	public static void insertDir(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				try {
					String name = files[i].getName();
					if (files[i].isDirectory()) {
						insertDir(files[i]);
					}
					if (name.endsWith(".zip")) {
						if (pro.getProperty(name) == null) {
							File file2 = files[i];
							Future<String> future = executorService.submit(new Runnable() {
								public void run() {
									insertZip(file2);
									pro.setProperty(name, "1");
									pro.setProperty("totalCount", String.valueOf(totalCount));
									// currentFileCount=0;
									// pro.setProperty( "currentFileCount",
									// String.valueOf(currentFileCount) );
								}
							}, name);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static synchronized void savePro() {
		long lastMinuteCount = totalCount.get();
		long nowCount;
		while(true){
			try {
				synchronized (pro) {
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++保存配置文件 ");
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++保存配置文件 ");
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++保存配置文件 ");
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++保存配置文件 ");
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++保存配置文件 ");
					PropertiesUtil.saveProp(pro, filePath);
					nowCount = totalCount.get();
					LOG.info("=========================================================================当前总数"+nowCount + " 上分钟新增 --------" + (nowCount-lastMinuteCount) );
					lastMinuteCount = nowCount;
				}

				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void insertZip(File file) {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
			insertZipInputStream(zis);
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertDir(String dir) {
		// File file = new File("E:/insert");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				savePro();
			}
		});
		thread.setDaemon(true);
		thread.start();
		File file = new File(dir);
		insertDir(file);

	}
	
	public static void put(List<News> newsList) throws IOException{
		Table hTable = conn.getTable(tableName);
		List<Put> puts = NewsUtil.genPuts(newsList);
		hTable.put(puts);
		hTable.close();
		newsList.clear();
		puts.clear();
	}
	
	public static void insertZipInputStream(ZipInputStream zis) throws Exception {
		ZipEntry zipEntry;
		while ((zipEntry = zis.getNextEntry()) != null) {
			InputStream is = zis;
			byte[] buf = new byte[2048];
			int read;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			while ((read = is.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			String fileName = zipEntry.getName();
			if (fileName.endsWith(".zip")) {
				insertZipInputStream(new ZipInputStream(new ByteArrayInputStream(bos.toByteArray())));
				continue;
			}
			String currentFileCountStr = pro.getProperty(fileName);
			int currentFileCount = 0;
			if (currentFileCountStr != null) {
				currentFileCount = Integer.parseInt(currentFileCountStr);
				if (currentFileCount == 1) {
					System.out.println(fileName + "  ++++ 已经解压 跳过......   ");
					continue;
				}
			}
			;
			if (currentFileCount == 0)
				System.out.println(fileName + "  ++++++++ 开始解压  ");
			String content = new String(bos.toByteArray());
			try {
				JSONObject jo1 = null;
				try {
					jo1 = new JSONObject(content).getJSONObject("GetWISEBulkJSONResult");
				} catch (Exception e) {
					jo1 = new JSONObject(content).getJSONObject("GetBulkbySTCResult");
				}
				JSONArray ja = jo1.getJSONArray("ResponseArray");
				List<News> newsList = new LinkedList<>();
				for (int i = currentFileCount; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					String id = null; // byte[] id ;
					int mediaType = 1;// 媒体类型id
					String mediaTname = "新闻";// 新闻
					// SimpleDateFormat sdf = new SimpleDateFormat("MMM
					// dd,yyyy",Locale.ENGLISH);
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String titleSrc = jo.getString("HeadLine");
					String pubdate = sdf2
							.format(new Date(Long.parseLong(jo.getString("PublicationDate").replaceAll("[^0-9]", "")))); // 发布时间
					String textSrc = jo.getString("BodyText").replaceAll("\\u000a", "##br##").replaceAll("\n",
							"##br##");

					String websiteId = null;
					String mediaNameSrc = jo.getString("SiteName");

					String mediaNameZh = null;
					String mediaNameEn = null;
					Integer mediaLevel = 4;
					String countryNameEn = jo.getString("Location");
					String countryNameZh = DicMap.getCountryZh(countryNameEn);
					String provinceNameZh = null;
					String provinceNameEn = null;
					String districtNameZh = null;
					String districtNameEn = null;

					String languageCode = DicMap.getLanguageEn(jo.getString("Language"));// en
																							// zh
					String languageTname = DicMap.getLanguageZh(languageCode);

					String url = jo.getString("UrlAddress");

					String author = null;

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
							mediaNameZh, mediaNameEn, mediaLevel.intValue(), countryNameZh, countryNameEn,
							provinceNameZh, provinceNameEn, districtNameZh, districtNameEn, languageCode, languageTname,
							author, created, updated, isOriginal, view, url, docLength, transFromM, pv, isHome,
							isPicture, comeFrom, null, null);

					news.setComeFromDb("CisionTxt");
					newsList.add(news);
//					if(newsList.size()==100){
//						put(newsList);
//					}
				}
				put(newsList);
				long tempTotal = totalCount.addAndGet(ja.length());
				System.out.println(fileName + "  ++++ 解压完毕   共有 " + (currentFileCount + newsList.size()) + " 文件数量 "
						+ ja.length() + "解压后总数————"+ tempTotal);
				pro.setProperty(fileName, String.valueOf(1));
				pro.setProperty("totalCount", String.valueOf(totalCount.get()));
				newsList.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
			zis.closeEntry();
		}

	}
	
	public static void main(String[] args) throws Exception {
		insertDir("/home/zyyt/cisionData");
		// insertDir("E:\\insert");
	}
}
