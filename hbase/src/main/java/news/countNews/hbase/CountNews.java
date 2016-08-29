package news.countNews.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import news.News;
import news.NewsDao;
import tw.utils.MyObjectFileStream;
import tw.utils.MysqlUniversalDao;
import tw.utils.PropertiesUtil;

public class CountNews {
	static final ExecutorService executorService = Executors.newFixedThreadPool(20);
	public static Connection connection;
	static MysqlUniversalDao mysqlUniversalDao;
	static Map<String, String> sourceMap;
	static Map<String, String> languageMap;
	static Map<String, String> mediaMap;
	static Map<String, String> errorMap;
	static List<Map> maps;
	public static Map<String, Integer> dailyCount = new ConcurrentHashMap<>();
	public static Map<String, Integer> totalCount = new ConcurrentHashMap<>();

	
	static {
		String JDBC_DRIVER = MysqlConfig.JDBC_DRIVER;
		String JDBC_URL = MysqlConfig.JDBC_URL;
		String JDBC_USER = MysqlConfig.JDBC_USER;
		String JDBC_PWD = MysqlConfig.JDBC_PWD;
		String TABLE_NAME = MysqlConfig.TABLE_NAME;

		connection = null;
		try {
			Object object = MyObjectFileStream.getObjFromFile(CountNews.class.getResource("/").getPath() + "map.dat");
			maps = (List) object;
			mediaMap = maps.get(0);
			languageMap = maps.get(1);
			sourceMap = maps.get(2);
			errorMap = maps.get(3);
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
			mysqlUniversalDao = new MysqlUniversalDao(connection, TABLE_NAME, new Count());
		} catch (Error | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	public Set<Count> getFrom(String sql) throws SQLException {
		Set<Count> counts = new HashSet<>();
		Count count = null;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			count = (Count) mysqlUniversalDao.getObjFromR(rs, 1);
			counts.add(count);
		}

		return counts;

	}

	static String getKey(Map<String, String> map, String value) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		System.out.println(value);
		return map.get("其他");
	}

	static void initMapByDate(String date) throws SQLException {

		CountNews countNews = new CountNews();
		// countNews.mysqlUniversalDao.insert(count);
		Set<Count> counts = countNews.getFrom(
				"select * from t_data_record_logs where RL_SOURCES_ID != 7 and RL_SOURCES_ID != 8 and RL_SOURCES_ID !=  9 and RL_NODEDATE >= "
						+ date);
		for (Count count2 : counts) {
			StringBuffer sb = new StringBuffer();
			sb.append(getKey(sourceMap, count2.RL_SOURCES_ID)).append("_").append(getKey(mediaMap, count2.RL_MEDIA_ID))
					.append("_").append(getKey(languageMap, count2.RL_LANGUAGE_ID)).append("_")
					.append(getKey(errorMap, count2.RL_ERROR));
			if (count2.getRL_IS_INCRE() == 1)
				dailyCount.put(sb.toString(), count2.RL_COUNT);
			else {
				totalCount.put(sb.toString(), count2.RL_COUNT);
			}
		}
		System.out.println("初始化完成。");
	}
	
	@SuppressWarnings("all")
	public static void main(String[] args) throws Exception {
		// 自动更新模式：
		NewsDao newsDao = new NewsDao(false) {

			class Scanner implements Callable<Map> {
				
				Map map;
				Scan scan;
				public Scanner(Map map, Scan scan) {
					this.map = map;
					this.scan = scan;
				}
				
				@Override
				public Map call() throws Exception {
					// TODO Auto-generated method stub

					ResultScanner rs = wTableLog[random.nextInt(tableN)].getScanner(scan);
					int i = 0;
					for (Result r : rs) {

						News news = getNewsFromR(r);
						String error = checkError(news);
						StringBuffer sb = new StringBuffer();

						String count_1st = null;
						String count_2nd1 = null;
						String count_2nd2 = null;
						String count_3rd = null;
						// System.out.println(i++ + news.getComeFrom());
						// if(!news.getComeFrom().equals("Hailiang")){
						// continue;
						// }
						if (news.getComeFrom().equals("Cision")) {
							sb.append(news.getComeFromDb());
						} else {
							sb.append(news.getComeFrom());
						}
						count_1st = sb.toString() + "_全部_全部_" + error;

						addCount(map, count_1st, 1);
						addCount(totalCount, count_1st, 1);

						count_2nd2 = sb.toString();

						sb.append("_").append(news.getMediaTname());
						count_2nd1 = sb.toString() + "_全部_" + error;

						addCount(totalCount, count_2nd1, 1);
						addCount(map, count_2nd1, 1);

						count_2nd2 += "_全部_" + news.getLanguageTname() + "_" + error;
						addCount(totalCount, count_2nd2, 1);
						addCount(map, count_2nd2, 1);

						sb.append("_").append(news.getLanguageTname());
						count_3rd = sb.toString() + "_" + error;
						addCount(map, count_3rd, 1);

					}
					rs.close();
					return map;
				}
			}

			@Override
			public void countBetween(Date beginDate, Date endDate, Map<String, Integer> sourceCount) {
				Map<String, Integer> sourceCountTemp = new ConcurrentHashMap<>();

				List<Future> futures = new ArrayList<>();
				try {
					long beginMills = System.currentTimeMillis();
					for (int i = 0; i < 100; i++) {
						String startF = null;
						if (beginDate == null) {
							startF = String.format("%02d", i) + (endDate.getTime() - 24 * 60 * 60 * 1000);
						} else {
							startF = String.format("%02d", i) + beginDate.getTime();
						}
						String endF = String.format("%02d", i) + endDate.getTime();

						Filter filter = new SingleColumnValueFilter(Bytes.toBytes("I"), Bytes.toBytes("comeFrom"),
								CompareOp.EQUAL, Bytes.toBytes("Hailiang"));

						byte[] startRow = Bytes.padTail(Bytes.toBytes(startF), 20);
						byte[] endRow = Bytes.padTail(Bytes.toBytes(endF), 20);
						Scan scan = new Scan(startRow, endRow);
						// scan.setFilter(filter);
						futures.add(executorService.submit(new Scanner(sourceCountTemp, scan)));

					}
					for (int i = 0; i < futures.size(); i++) {
						futures.get(i).get();
					}
					long hbaseTime = (System.currentTimeMillis() - beginMills) / 1000;
					addCount(sourceCount, sourceCountTemp);
					System.out.println("Hbase time spend : " + hbaseTime);

					System.out.println("Count time spend : " + (System.currentTimeMillis() - beginMills) / 1000);
					// saveToDatabase(sourceCount, beginDate, 1);
					// saveToDatabase(totalCount, beginDate, 0);
					System.out.println("Save time spend : " + (System.currentTimeMillis() - beginMills) / 1000);

				} catch (Exception e) {
					LOG.error(e);
					e.printStackTrace();
				}
			}

			private String checkError(News news) {
				if (isNull(news.getMediaNameSrc()) && isNull(news.getMediaNameEn()) && isNull(news.getMediaNameEn())) {
					return "缺少网站名称";
				} else if (isNull(news.getCountryNameEn()) && isNull(news.getCountryNameZh())) {
					return "缺少国家字段";
				} else if (isNull(news.getLanguageCode()) && isNull(news.getLanguageTname())) {
					return "缺少语言字段";
				} else if (!isLineFeed(news.getTextSrc())) {
					return "缺少正文换行标签";
				}
				return "无问题";
			}

			private boolean isNull(String str) {
				if (str == null || str.equals("") || str.toLowerCase().equals("null")) {
					return true;
				}
				return false;
			}

			private boolean isLineFeed(String str) {

				String regex = "<br>|<br/>|</br>|<p>|</p>|\r\n|\n|\\\\n|\\n|##br##";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(str.toLowerCase().replaceAll("\\s", ""));
				if (matcher.find()) {
					return true;
				}
				return false;
			}

			void saveToDatabase(Map<String, Integer> sourceCount, Date date, int rL_IS_INCRE) {

				for (Map.Entry<String, Integer> entry : sourceCount.entrySet()) {
					Count count = new Count();
					String[] keys = entry.getKey().split("_");
					int value = entry.getValue();

					count.setRL_SOURCES_ID(sourceMap.get(keys[0]));
					count.setRL_MEDIA_ID(mediaMap.get(keys[1]));
					count.setRL_LANGUAGE_ID(languageMap.get(keys[2]));
					count.setRL_IS_INCRE(rL_IS_INCRE);
					count.setRL_ERROR(errorMap.get(keys[3]));

					count.setRL_NODEDATE(date);
					count.setRL_COUNT(value);

					if (rL_IS_INCRE == 0) {
						// if (!mysqlUniversalDao.update(count, 5)) {
						mysqlUniversalDao.insert(count, 6, true);
						// }
					} else
						mysqlUniversalDao.insert(count, 6, true);
				}
			}
		};

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfD = new SimpleDateFormat("dd");
		SimpleDateFormat sdfSqlDate = new SimpleDateFormat("yyyy-MM-dd");
		String filePath = NewsDao.class.getResource("/").getPath() + "countNews2.properties";
		Properties properties = PropertiesUtil.getProp(filePath);
		Date lastTaskDate = dateFormat.parse(properties.getProperty("lastTaskDate", "2016-07-05 00:00:00"));

		Date nextDate = null;
		initMapByDate(sdfSqlDate.format(lastTaskDate));
		// 初始化task；

		// BufferedReader bf = new BufferedReader(new FileReader(new
		// File("E:/insert/"+"count"+sdf.format(lastTaskDate)+".txt")));

		while (true) {
			try {
				nextDate = new Date(lastTaskDate.getTime() + 60 * 1000);

				if (!(sdfD.format(lastTaskDate).equals(sdfD.format(nextDate)))) {
					CountNews.dailyCount.clear();
				}
				if (System.currentTimeMillis() >= nextDate.getTime()) {
					newsDao.countBetween(lastTaskDate, nextDate, CountNews.dailyCount);
					lastTaskDate = new Date(lastTaskDate.getTime() + 60 * 1000);

					System.out.println("新任务：" + lastTaskDate);
					properties.setProperty("lastTaskDate", dateFormat.format(lastTaskDate));
					PropertiesUtil.saveProp(properties, filePath);
				} else {
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
