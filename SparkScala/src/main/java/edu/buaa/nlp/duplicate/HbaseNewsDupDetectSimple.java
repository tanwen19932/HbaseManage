package edu.buaa.nlp.duplicate;

import com.google.common.base.Stopwatch;
import controller.Constants;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.entity.util.HbaseUtil;
import edu.buaa.nlp.util.HashAlgorithms;
import edu.buaa.nlp.utils.texthash.TextHash;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HbaseNewsDupDetectSimple implements IDupDetector<ProcessedNews> {
	private static Logger logger = Logger.getLogger(HbaseNewsDupDetectSimple.class);
	static HbaseNewsDupDetectSimple instance;
	private static final byte[] family = "D".getBytes();
	private static final String[] familys = { "D" };
	public static Connection conn;

	private static synchronized void syncInit() {
		if (instance == null) {
			try {
				instance = new HbaseNewsDupDetectSimple();
				conn = ConnectionFactory.createConnection(HbaseUtil.getConf());
			} catch (IOException e) {
				logger.error("初始化去重失败！错误 退出！");
				System.exit(0);
				e.printStackTrace();
			}
		}
	}

	public static HbaseNewsDupDetectSimple getInstance() {
		while (instance == null) {
			syncInit();
		}
		return instance;
	}

	private HbaseNewsDupDetectSimple() {
		
	}

	@Override
	public String isDup(ProcessedNews news) {
		String returnStr = "0";
		try {
			boolean isDup = false;
			String tableSuffix = news.getPubdate().substring(2, 7);
			if(tableSuffix.compareTo("20-01")>0 || tableSuffix.compareTo("10-01")<0){
				tableSuffix="10-01";
			}
			TableName tableName = TableName.valueOf(Constants.HBASE_DUP_TABLE +tableSuffix );
			Table table = conn.getTable(tableName);
			long mediaHashcode = HashAlgorithms.mixHash(news.getMediaNameSrc());
			long titleHashcode = HashAlgorithms.mixHash(news.getTitleSrc());
			long contentSimHash = TextHash.computeSimHashForString(news.getTextSrc());
			String key = String.valueOf(titleHashcode) + "|" + String.valueOf(contentSimHash) + "|"
					+ String.valueOf(mediaHashcode);
			Get get = new Get(Bytes.toBytes(key));
			Stopwatch stopwatch = new Stopwatch();
			stopwatch.start();
			isDup = check(get, table);
			logger.debug("去重判断 exist时间 {} "+stopwatch.elapsedMillis());
			if (isDup == true) {
				returnStr = "1";
			}
			else{
				Put put = new Put(Bytes.toBytes(String.valueOf(key)));
				put.addColumn(family, Bytes.toBytes(""), Bytes.toBytes(""));
				stopwatch.reset().start();
				table.put(put);
				logger.debug("去重插入时间 {} "+stopwatch.elapsedMillis());
				saveDup(news);
			}
			return returnStr;
		} catch (Exception e) {
			logger.error("去重时遇到错误 ！" ,e);
			return returnStr;
		}
	}

	public boolean check(Get get, Table table) {
		try {
			boolean re = table.exists(get);
			return re;
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				if (!conn.getAdmin().tableExists(table.getName())) {
					createTable(table.getName().getNameAsString());
					return check(get, table);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean saveDup(ProcessedNews news) {
		// logger.info("去重保存！");
		return true;
	}

	public synchronized void createTable(String tableName) {
		try {
			logger.info("===Create Table：  " + tableName + " +++++++++++++++++++++");
			Admin admin = conn.getAdmin();
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
			for (String f : familys) {
				HColumnDescriptor descriptor = new HColumnDescriptor(f);
				Algorithm compression = Algorithm.SNAPPY;
				descriptor.setCompressionType(compression);
				tableDescriptor.addFamily(descriptor);
			}
			byte[][] regions = new byte[][] { 
												// CreateTableWithRegionsExample-5-Regions
												// Manually create region split
												// keys.
					Bytes.toBytes("-200000000"), Bytes.toBytes("-150000000"), Bytes.toBytes("-100000000"),
					Bytes.toBytes("-50000000"), Bytes.toBytes("0"), Bytes.toBytes("5000000"),
					Bytes.toBytes("100000000"), Bytes.toBytes("150000000"), Bytes.toBytes("200000000") };
			admin.createTable(tableDescriptor,regions);// 指定splitkeys
			logger.info("===Create Table " + tableName + " Success!");
			if (!admin.tableExists(TableName.valueOf(tableName))) {
				createTable(tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) {
		Long now = System.currentTimeMillis();
		while(true){
			String tableSuffix = edu.buaa.nlp.tw.common.DateUtil.getDateStr("yy-MM", now);
			if(tableSuffix.compareTo("20-01")>0 || tableSuffix.compareTo("10-01")<0){
				break;
			}
			try {
				HbaseUtil.deleteTable("NewsDupHash"+tableSuffix);
			} catch (Exception e) {
				e.printStackTrace();
			}
			now = now -1000*60*3;
			
		}
		String tableSuffix = "09-01";
		if(tableSuffix.compareTo("20-01")>0 || tableSuffix.compareTo("10-01")<0){
			tableSuffix="10-01";
		}
		System.out.println(tableSuffix);
	}
}
