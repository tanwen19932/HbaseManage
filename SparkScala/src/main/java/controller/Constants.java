package controller;

import edu.buaa.nlp.util.FileUtil;

import java.io.File;
import java.util.Properties;

public class Constants {

	
	public static int PRODUCE_DATA_TYPE = 1;	//1-news;2-weibo;3-weibo comment
	public static String PRODUCE_DATA_LANGUAGE="";	//当为空时，表示全部
	
	
	public static int NUM_PROCESS_NEWS_PERTIME=2000;
	public static int NUM_PROCESS_WEIBO_PERTIME=500;
	public static int NUM_PROCESS_WEIBO_COMMENT_PERTIME=500;
	public static int NUM_PROCESS_PRODUCT_COMMENT_PERTIME=500;
	public static String DATA_SERVICE_URL;
	public static String DATA_CONTROLLER_URL;
	
	public static String HASH_FILE_PATH = "data/duplicate/";
	public static String HASH_FILE_PATH_WEB = "data/duplicate/jw_web.dat";
	public static String HASH_FILE_PATH_UUID = "data/duplicate/jw_uuid.dat";
	public static String TRANSFER_FILE_PATH = "data/duplicate/jw_transfer.dat";	
	
	public static double DUPLICATE_THRESHOLD=0.85;
	public static long DUPLICATE_BUFF_SIZE = 100 * 1024;	//排重用的 
	
	//容器大小
	public static int CONTAINER_CORE_SIZE=5000; //默认的容器大小为5000，即容器大小小于5000时，就需要启动去获取数据
	//读取的表明
	public static String READ_TABLE_NAME = "NewsArticleBE2";
	public static String HBASE_DUP_TABLE = "NewsDupHash";
	public static String READ_COME_FROM = "";
	public static String BEGIN_DATE_TIME = "2000-01-01 00:00:00";
	public static String END_DATE_TIME = "2016-12-31 00:00:00";
	
	public static int READ_TABLE_THREAD=10;
	public static int PREPROCESS_THREAD_NUM=3; //预处理线程
	public static String SPLIT_CONTAINERS="2016-01-01,2016-12-31,[en,zh]";
	
	public static int TRIED_FETCH_DATA=10; //
	public static String RAW_JSON_PATH="raw/";
	public static String DUP_JSON_PATH="dup/";
	public static String MYSQL_TABLE = "counter_hbase_to_es";
	public static int MONITER_STATE_TIME=10000; //跟踪状态，刷新一次时间默认10s
	public static String isDeleteHash = "0";
	
	static{
		Properties pro=FileUtil.getPropertyFile("config.properties");
		String val=pro.getProperty("document_num_pertime");
		if(val!=null && !"".equals(val)){
			NUM_PROCESS_NEWS_PERTIME=Integer.parseInt(val);
		}
		val=pro.getProperty("data_service_url");
		if(val!=null && !"".equals(val)){
			DATA_SERVICE_URL=val;
		}
		
		DATA_CONTROLLER_URL=pro.getProperty("data_controller_url","").trim();
		
		val=pro.getProperty("process_data_type");
		if(val!=null && !"".equals(val)){
			PRODUCE_DATA_TYPE=Integer.parseInt(val);
		}
		MYSQL_TABLE = pro.getProperty("MYSQL_TABLE","counter_hbase_to_es").trim();
		HBASE_DUP_TABLE = pro.getProperty("HBASE_DUP_TABLE","NewsDupHash").trim();
		HASH_FILE_PATH=pro.getProperty("HASH_FILE_PATH","data/duplicate/").trim();
		PRODUCE_DATA_LANGUAGE=pro.getProperty("PRODUCE_DATA_LANGUAGE","").trim();
		System.out.println("读取的语言类型 ："+ PRODUCE_DATA_LANGUAGE);
		DUPLICATE_BUFF_SIZE=Long.parseLong(pro.getProperty("DUPLICATE_BUFF_SIZE", "102400"));
		
	    isDeleteHash = pro.getProperty("isDeleteHash", "0");
		
		
		val=pro.getProperty("DUPLICATE_THRESHOLD");
		if(val!=null && !"".equals(val)){
			try
			{
				DUPLICATE_THRESHOLD=Double.parseDouble(val);
			}
			catch(Exception e)
			{
				DUPLICATE_THRESHOLD = 0.85;
			}
		}
		CONTAINER_CORE_SIZE=Integer.parseInt(pro.getProperty("CONTAINER_CORE_SIZE", "5000"));
		
		READ_TABLE_NAME=pro.getProperty("READ_TABLE_NAME", "NewsArticleBE2").trim();
		READ_COME_FROM=pro.getProperty("READ_COME_FROM", "").trim();
		BEGIN_DATE_TIME=pro.getProperty("BEGIN_DATE_TIME", "2000-01-01 00:00:00").trim();
		END_DATE_TIME=pro.getProperty("END_DATE_TIME", "2016-12-31 00:00:00").trim();
		
		NUM_PROCESS_PRODUCT_COMMENT_PERTIME=Integer.parseInt(pro.getProperty("NUM_PROCESS_PRODUCT_COMMENT_PERTIME", "200"));
		
		READ_TABLE_THREAD=Integer.parseInt(pro.getProperty("READ_TABLE_THREAD", "10"));
		PREPROCESS_THREAD_NUM=Integer.parseInt(pro.getProperty("PREPROCESS_THREAD_NUM", "3"));
		SPLIT_CONTAINERS=pro.getProperty("SPLIT_CONTAINERS","2016-01-01,2016-12-31,[en,zh]").trim();
		
		RAW_JSON_PATH=pro.getProperty("RAW_JSON_PATH", "raw").trim() + File.separator;
		DUP_JSON_PATH=pro.getProperty("DUP_JSON_PATH", "dup").trim() + File.separator;
		FileUtil.createDir(RAW_JSON_PATH);
		FileUtil.createDir(DUP_JSON_PATH);
		
		MONITER_STATE_TIME=Integer.parseInt(pro.getProperty("MONITER_STATE_TIME", "10000"));
	}
	public static final String ENCODING_DB = "utf-8";
	public static final String SEG_SEPARATOR="#";
	public static String SourceDBUrl;
	public static String SourceDBUser;
	public static String SourceDBPassword;

	public static String DBUrl;
	public static String DBUser;
	public static String DBPassword;
	
}
