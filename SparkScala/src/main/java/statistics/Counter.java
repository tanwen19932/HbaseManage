package statistics;

import controller.Constants;
import edu.buaa.nlp.tw.common.FileUtils;
import edu.buaa.nlp.tw.common.MyObjectFileStream;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class Counter {
	static Map<CountModel, Long> totalCount = new ConcurrentHashMap<>();
	static Map<CountModel, Long> count = new ConcurrentHashMap<>();
	private static Logger logger = Logger.getLogger(Counter.class);
	private static Date lastDate = new Date();
	private static String filePath = "statistics";
	private static final String TEMP_FILE_DAT = "tempTodayCount.dat";
	private static final String TEMP_FILE_TXT = "tempTodayCount.txt";
	private static final String tableName = Constants.MYSQL_TABLE ;
	static {
		try {
			 logger.info("初始化每日统计..............." + tableName);
			// initFromFile();
			// initFromMysql();
		} catch (Exception e) {
			count = new ConcurrentHashMap<>();
		}

		Thread countPrint = new Thread(new Runnable() {
			@Override
			public void run() {
				int i=0;
				int saveInterval = 3;
				while (true) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date nowDate = new Date();
					if (!sdf.format(nowDate).equals(sdf.format(lastDate))) {
						logger.info("日期变更 清空每日统计...............");
						// saveDayFile();
						saveToMysql();
						lastDate = nowDate;
					}
					printStatistics();
					i++;
					// saveTempFile();
					
					if(i==saveInterval){
						logger.info("保存前" + i +"分钟统计结果...............");
						saveToMysql();
						i=0;
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		countPrint.start();
	}

	public static void increase(CountModel model) {
		increaseValue(model, 1);
	}

	public static void initFromMysql() throws Exception {
		PreparedStatement ps = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ps = MysqlConfig.getConnection().prepareStatement(
					"select comeFrom,errorMessage,languageCode,countTime,max(countNum)  from " + tableName +" a  where  countTime = "
							+ "\"" + sdf.format(new Date()) + "\"" + " group by comeFrom,errorMessage,languageCode;");
			logger.info(ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count.put(new CountModel(rs.getString("comeFrom"), ERROR.valueOf(rs.getString("errorMessage")),
						rs.getString("languageCode"), rs.getString("countTime")), rs.getLong("max(countNum)"));
			}
			rs.close();
			ps.close();
			for (Entry<CountModel, Long> temp : count.entrySet()) {
				logger.info(temp.getKey() + " = " + temp.getValue());
			}
			logger.info("每日统计结果初始化完成 ");
		} catch (SQLException e) {
			logger.error("保存统计结果出错 " + ps.toString());
			logger.error("",e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initFromFile() throws Exception {
		count = (Map<CountModel, Long>) MyObjectFileStream.getObjFromFile(TEMP_FILE_DAT);
		if (count.keySet().size() != 0) {
			CountModel temp = count.keySet().iterator().next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			lastDate = sdf.parse(temp.countTime);
		}
	}

	public static void saveDayFile() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		synchronized (count) {
			for (Entry<CountModel, Long> temp : count.entrySet()) {
				FileUtils.fileAppendStr(filePath + File.separator + sdf.format(lastDate),
						(temp.getKey() + " = " + temp.getValue()));
			}
			count.clear();
		}
	}

	public static void saveTempFile() {
		StringBuilder sb = new StringBuilder();
		for (Entry<CountModel, Long> temp : count.entrySet()) {
			logger.info(temp.getKey() + " = " + temp.getValue());
			sb.append(temp.getKey() + " = " + temp.getValue()).append("\r\n");
		}
		try {
			FileUtils.writeFile(TEMP_FILE_TXT, sb.toString(), "utf-8");
			synchronized (count) {
				MyObjectFileStream.saveToFile(TEMP_FILE_DAT, count);
			}
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void increaseValue(CountModel model, long i) {
		
			if (count.containsKey(model)) {
					count.put(model, count.get(model) + i);
			} else {
				count.put(model, i);
			}
	}

	private static void saveToMysql() {
		try {
			synchronized (count) {
				for (Entry<CountModel, Long> temp : count.entrySet()) {
					try {
						logger.info(temp.getKey() + " = " + temp.getValue());
						insertMysql(temp);
					} catch (Exception e) {
						logger.error("保存统计结果出错 ");
						logger.error("",(e));
					}
				}
				count.clear();
				logger.info("保存统计结果成功并清空———— ");
			}
		} catch (Exception e) {
			logger.error("保存统计结果出错 ");
			logger.error("",(e));
		}
	}

	private static void insertMysql(Entry<CountModel, Long> temp) throws Exception {

		PreparedStatement ps = null;
		ps = MysqlConfig.getConnection().prepareStatement(
				"INSERT INTO " + tableName +" (comeFrom ,errorMessage,languageCode,countNum)  VALUES (?, ?, ?, ?);");
		ps.setString(1, temp.getKey().comeFrom);
		ps.setString(2, temp.getKey().errorMessage.name());
		ps.setString(3, temp.getKey().language );
		ps.setLong(4, temp.getValue());
		try {
			ps.execute();
		} catch (Exception e) {
			throw  new Exception(e.getMessage() + ps.toString());
		}
		ps.close();
	}

	private static void updateMysql(Entry<CountModel, Long> temp) throws Exception {
		PreparedStatement ps = null;
		ps = MysqlConfig.getConnection().prepareStatement(
				"update " + tableName +" set countNum = ? where comeFrom = ? and errorMessage = ? and languageCode = ? and countTime = ?");
		ps.setLong(1, temp.getValue());
		ps.setString(2, temp.getKey().comeFrom);
		ps.setString(3, temp.getKey().errorMessage.name());
		ps.setString(4, temp.getKey().language);
		ps.setString(5, temp.getKey().countTime);
		try {
			ps.execute();
		} catch (Exception e) {
			throw  new Exception(e.getMessage() + ps.toString());
		}
		ps.close();
	}
	
	private static void printStatistics(){
		for (Entry<CountModel, Long> temp : count.entrySet()) {
			logger.info(temp.getKey() + " = " + temp.getValue());
		}
	}
}