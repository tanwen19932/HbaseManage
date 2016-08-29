package news.hailiangNews.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import news.News;
import news.NewsDao;
import tw.utils.PropertiesUtil;
import tw.utils.ZipUtil;

public class HailiangTxtFile {
	static Properties pro = null;
	static String filePath = HailiangTxtFile.class.getResource("/").getPath() + "Hailiang.properties";;
	// static String filePath = "E:/properties/cision.properties";
	static ExecutorService myThreadPool;
	static AtomicLong totalCount ;
	
	static {
		pro = new Properties();
		pro = PropertiesUtil.getProp(filePath);
		myThreadPool = Executors.newFixedThreadPool(2);
		try{
			totalCount = new AtomicLong(Long.parseLong(pro.getProperty("totalCount"))) ;
		}catch (Exception e){
			totalCount  = new AtomicLong();
		}
	}

	public static void insertDir(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			List<Future> futures = new ArrayList<>();
			for (int i = 0; i < files.length; i++) {
				try {
					String name = files[i].getName();
					if (files[i].isDirectory()) {
						System.out.println("开始插入数据" + files[i].getName());
						insertDir(files[i]);
					} else if (!name.endsWith(".tar.gz"))
						continue;
					else if (pro.getProperty(name.replaceAll("\\..*", "")) == null) {
						futures.add(  
								myThreadPool.submit(new Inserter(files[i])) 
								);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for(Future future: futures){
				try {
					future.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} else
			try {
				if (!file.getName().endsWith(".tar.gz"))
					myThreadPool.submit(new Inserter(file));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void insertDir(String dir) {
		// File file = new File("E:/insert");
		File file = new File(dir);
		insertDir(file);

	}

	static class Inserter implements Callable<String> {
		File file;

		public Inserter(File file) {
			this.file = file;
		}

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			NewsDao newsDao = new NewsDao();
			String fileName = file.getName();
			ZipUtil zipUtil = null;
			try {
				zipUtil = new ZipUtil(file.getAbsolutePath());
//				String aString = ZipUtil.getTarGzFileStr(file.getAbsolutePath());
				String line;
				while ((line = zipUtil.nextLine()) != null) {
					try {
						String[] str = line.split("\\^");

						String id = null; // byte[] id ;
						int mediaType = 1;// 媒体类型id
						String mediaTname = "新闻";// 新闻
						// SimpleDateFormat sdf = new SimpleDateFormat("MMM
						// dd,yyyy",Locale.ENGLISH);
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						String titleSrc = str[2];
						String pubdate = str[5]; // 发布时间
						String textSrc = str[6];

						String websiteId = null;
						String mediaNameSrc = str[3];

						String mediaNameZh = null;
						String mediaNameEn = str[3];
						Integer mediaLevel = 4;
						String countryNameEn = "China";
						String countryNameZh = "中国";
						String provinceNameZh = null;
						String provinceNameEn = null;
						String districtNameZh = null;
						String districtNameEn = null;

						String languageCode = "zh";// en
													// zh
						String languageTname = "中文";

						String url = str[0];

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

						String comeFrom = "Hailiang";
						News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId,
								mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel.intValue(), countryNameZh,
								countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn,
								languageCode, languageTname, author, created, updated, isOriginal, view, url, docLength,
								transFromM, pv, isHome, isPicture, comeFrom, null, null);
						news.setComeFromDb(fileName);
						newsDao.Insert(news);
						System.out.println("insert success !" + totalCount.incrementAndGet());
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println(Thread.currentThread().getName() + "wrong "+ file.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if( zipUtil!=null ){
					zipUtil.close();
				}
			}
			pro.setProperty( fileName.replaceAll("\\..*", ""), "1" );
			pro.setProperty( "totalCount", String.valueOf(totalCount) );
			PropertiesUtil.saveProp(pro, filePath);
			return fileName;
		}

	}


	public static void main(String[] args) throws Exception {
		System.out.println("开始插入数据");
		insertDir("/home/zyyt/hailiang");
//		 insertDir("E:\\insert\\hailiang");
	}
}
