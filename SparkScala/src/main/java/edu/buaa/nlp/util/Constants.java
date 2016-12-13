package edu.buaa.nlp.util;

import java.io.File;
import java.util.Properties;

public class Constants {

	
	public static int PRODUCE_DATA_TYPE = 1;	//1-news;2-weibo;3-weibo comment
	
	public static final String ENCODING_DB = "utf-8";
	public static final int NUM_PROCESS_NEWS_PERTIME=300;
	public static final int NUM_KEYWORD_EXTRACT=10;
	
	public static int TRIED_TRANSLATE_TIME =3 ; //翻译尝试多少次
	
	public static int MONITER_STATE_TIME=10000; //跟踪状态，刷新一次时间默认10s

	public static final String THEMECLASSIFICATION_DIR = "ThemeClassify/";

	// public static final String CnCorrelation_DIR = "Correlation/";

	public static final String RESOURCE_PATH = "resource/";

	public static final String USER_DICT_FILE = "./userdict.txt";
	public static final String PARSER_FILE = "./Data/lexparser/chineseFactored.ser.gz";
	public static final String PARSER_FILE2 = "./Data/lexparser/chinesePCFG.ser.gz";

	// public static final String RUN_TAG = "BUAA-NLPGroup";
	public static final String RUN_TAG = "BUAA-SA_2";
	public static final String OPINIONED_TAG = "Y";
	public static final String NON_OPINIONED_TAG = "N";

	public static final String POS_TAG = "POS";
	public static final String NEG_TAG = "NEG";
	public static final String OTHER_TAG = "OTHER";

	public static String SourceDBUrl;
	public static String SourceDBUser;
	public static String SourceDBPassword;

	public static String DBUrl;
	public static String DBUser;
	public static String DBPassword;

	public static String TRANSLATE_SERVER_URL;
	public static String DATA_CONTROLLER_URL;
	public static String WINUTIL_DIR;
	public static int HBASE_PUT_BUFFER_SIZE=1000;

	
	
	//数据库处理操作记T_geis_process_RECORD 	TYPE 标识
	public static int PROCESS_RECORD_TYPE_INDEX=1;
	public static int PROCESS_RECORD_TYPE_RELATION=2;
	public static int PROCESS_RECORD_TYPE_HOTSPOT=3;
	public static int PROCESS_RECORD_TYPE_HOTWORD=4;
	
	//情感分类标识
	public static final int SENTIMENT_POSITIVE=1;
	public static final int SENTIMENT_NEUTRAL=0;
	public static final int SENTIMENT_NEGATIVE=-1;
	
	//容器大小
	public static int CONTAINER_DEFAULT_SIZE=400;
	public static int CONTAINER_CORE_SIZE=200;
	
	public static int NUM_MERGE_NEWSPERTIME=100;
	public static int DELAY_MERGE_NEWSPERTIME = 3000;	//每次Merge后的延迟 ms
	
	//线程池
	public static final int POOL_CORE_SIZE=5;
	public static final int POOL_MAX_SIZE=Runtime.getRuntime().availableProcessors()*2+1;
	public static final int THREAD_WAIT_SECONDS=1000;
	public static final int THREAD_WAIT_SECONDS_LONG=1000*50;
	public static final int THREAD_WAIT_SECONDS_BLOCK=10;
	public static final int THREAD_WAIT_SECONDS_LINE=NUM_PROCESS_NEWS_PERTIME*100;
	public static String HBASE_TABLE_ARTICLE1="";
	public static String HBASE_TABLE_ARTICLE2="";
	public static String HBASE_TABLE_ARTICLE3="";
	
	public static String HBASE_TABLE_WEIBO="";
	public static String HBASE_TABLE_WEIBO_COMMENT="";
	
	public static int RESTART_TIME_GAP;
	public static char RESTART_TIME_UNIT;
	
	
	public static String HASH_FILE_PATH = "data/duplicate/";
	public static String HASH_FILE_PATH_WEB = "data/duplicate/jw_web.dat";
	public static String HASH_FILE_PATH_UUID = "data/duplicate/jw_uuid.dat";
	public static double DUPLICATE_THRESHOLD=0.85;
	
	public static String RAW_JSON_PATH = "raw/";
	
	static{
		Properties prop= FileUtil.getPropertyFile("config.properties");
		String val=prop.getProperty("translate_server_url");
		if(val==null || "".equals(val)){
			try {
				throw new Exception("未设置翻译服务器连接URL！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PRODUCE_DATA_TYPE=Integer.parseInt(prop.getProperty("PRODUCE_DATA_TYPE", "1"));
		
		DATA_CONTROLLER_URL=prop.getProperty("data_controller_url");
		WINUTIL_DIR=prop.getProperty("winutil_dir");
		TRANSLATE_SERVER_URL=val;
		HBASE_TABLE_ARTICLE1=prop.getProperty("hbase_table1", "NewsArticleTT2").trim();
		HBASE_TABLE_ARTICLE2=prop.getProperty("hbase_table2", "NewsArticleOA2").trim();
		HBASE_TABLE_ARTICLE3=prop.getProperty("hbase_table3", "NewsArticleBE2").trim();
		
		HBASE_TABLE_WEIBO=prop.getProperty("hbase_table_weibo", "Weibo").trim();
		HBASE_TABLE_WEIBO_COMMENT=prop.getProperty("hbase_table_weibo_comment", "WeiboComment").trim();
		
		NUM_MERGE_NEWSPERTIME=Integer.parseInt(prop.getProperty("NUM_MERGE_NEWSPERTIME", "1000"));
		DELAY_MERGE_NEWSPERTIME=Integer.parseInt(prop.getProperty("DELAY_MERGE_NEWSPERTIME", "3000"));
		CONTAINER_DEFAULT_SIZE=Integer.parseInt(prop.getProperty("CONTAINER_DEFAULT_SIZE", "2000"));
		CONTAINER_CORE_SIZE=Integer.parseInt(prop.getProperty("CONTAINER_CORE_SIZE", "500"));
		
		MONITER_STATE_TIME=Integer.parseInt(prop.getProperty("MONITER_STATE_TIME", "10000"));
		TRIED_TRANSLATE_TIME=Integer.parseInt(prop.getProperty("TRIED_TRANSLATE_TIME", "3"));
		
		DUPLICATE_THRESHOLD=Double.parseDouble(prop.getProperty("DUPLICATE_THRESHOLD", "0.85"));
		
		
		RESTART_TIME_GAP=Integer.parseInt(prop.getProperty("restart_time_gap", "3"));
		RESTART_TIME_UNIT=prop.getProperty("restart_time_unit", "H").charAt(0);
		
		RAW_JSON_PATH=prop.getProperty("RAW_JSON_PATH", "raw").trim() + File.separator;
		FileUtil.createDir(RAW_JSON_PATH);
		
	}
	public static void main(String[] args) {
		System.out.println(edu.buaa.nlp.util.Constants.RESTART_TIME_UNIT);
	}
}
