package edu.buaa.nlp.duplicate;

import controller.Constants;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.entity.util.HbaseUtil;
import edu.buaa.nlp.util.HashAlgorithms;
import edu.buaa.nlp.utils.texthash.TextHash;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HbaseNewsDupDetect implements IDupDetector<ProcessedNews> {
	private static Logger logger = Logger.getLogger(HbaseNewsDupDetect.class);
	static HbaseNewsDupDetect instance;
	private static final byte[] family = "D".getBytes();
	private static final String[] familys = {"D"};
	private static Connection conn;
	
	private static synchronized void syncInit() {
		if (instance == null) {
			try {
				instance = new HbaseNewsDupDetect();
				conn = ConnectionFactory.createConnection(HbaseUtil.getConf());
			} catch (IOException e) {
				logger.error("初始化去重失败！错误 退出！");
				System.exit(0);
				e.printStackTrace();
			}
		}
	}

	public static HbaseNewsDupDetect getInstance() {
		while (instance == null) {
			syncInit();
		}
		return instance;
	}

	private HbaseNewsDupDetect() {

	}

	@Override
	public String isDup(ProcessedNews news) {
		String returnStr = "0";
		try {
			boolean isDup = false;
			TableName tableName = TableName.valueOf(Constants.HBASE_DUP_TABLE + news.getPubdate().substring(2,7));
			Table table = conn.getTable(tableName);
			long mediaHashcode = HashAlgorithms.mixHash(news.getMediaNameSrc());
			long titleHashcode = HashAlgorithms.mixHash(news.getTitleSrc());
			long contentSimHash = TextHash.computeSimHashForString(news.getTextSrc());
			String value = contentSimHash + "|" + mediaHashcode;
			Get get = new Get(Bytes.toBytes(String.valueOf(titleHashcode)));
			isDup = check(get, table);
			if (isDup == true) {
				Result result = table.get(get);
				for (Cell c : result.rawCells()) {
					String origionUUID = Bytes.toString(CellUtil.cloneQualifier(c));
					String[] tempValue = Bytes.toString(CellUtil.cloneValue(c)).split("\\|");
					try {
						long oldContentHash = Long.valueOf(tempValue[0]);
						long oldMediaHash = Long.valueOf(tempValue[1]);
						if (TextHash.calcSimilarity(contentSimHash, oldContentHash) > Constants.DUPLICATE_THRESHOLD) {
							if (oldMediaHash == mediaHashcode)// 标题相等重复
							{
								returnStr = "1";

							} else {
//								logger.info("文章转载！" +  titleHashcode + "  " + origionUUID);
								returnStr = origionUUID;
							}
							break;
						}
					} catch (Exception e) {
						StringBuilder sb = new StringBuilder();
						for(String temp : tempValue){
							sb.append(temp);
						}
						logger.error("去重出现错误 " +tempValue.length + " :" + sb);
					}
				}
			}
			if (!returnStr.equals("1")) {
				Put put = new Put(Bytes.toBytes(String.valueOf(titleHashcode)));
				put.addColumn(family, Bytes.toBytes(news.getId()), Bytes.toBytes(value));
				table.put(put);
				saveDup(news);
			}
			return returnStr;
		} catch (Exception e) {
			logger.error("去重时遇到错误 ！" ,(e));
			return returnStr;
		}
	}
	public boolean check(Get get, Table table){
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
//		logger.info("去重保存！");
		return true;
	}
	
	public synchronized void createTable(String tableName){
		try {
			logger.info("===Create Table：  " + tableName  
                    + " +++++++++++++++++++++");  
			Admin admin = conn.getAdmin();
			 HTableDescriptor tableDescriptor = new HTableDescriptor(  
                     TableName.valueOf(tableName));  
			 for (String f : familys) {  
                 tableDescriptor.addFamily(new HColumnDescriptor(f));  
             }  
			    byte[][] regions = new byte[][] { // co CreateTableWithRegionsExample-5-Regions Manually create region split keys.
				      Bytes.toBytes("-200000000"),
				      Bytes.toBytes("-150000000"),
				      Bytes.toBytes("-100000000"),
				      Bytes.toBytes("-50000000"),
				      Bytes.toBytes("0"),
				      Bytes.toBytes("5000000"),
				      Bytes.toBytes("100000000"),
				      Bytes.toBytes("150000000"),
				      Bytes.toBytes("200000000")
				    };
             admin.createTable(tableDescriptor,regions);//指定splitkeys  
             logger.info("===Create Table " + tableName  
                     + " Success!");  
             if(!admin.tableExists(TableName.valueOf(tableName))){
            	 createTable(tableName);
             }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
