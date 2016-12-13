package edu.buaa.nlp.duplicate;

public class DupConstants {

	public static String HASH_FILE_PATH = "data/duplicate/";
	public static String HASH_FILE_PATH_WEB = "data/duplicate/jw_web.dat";
	public static String HASH_FILE_PATH_UUID = "data/duplicate/jw_uuid.dat";
	public static String TRANSFER_FILE_PATH = "data/duplicate/jw_transfer.dat";	
	
	public static double DUPLICATE_THRESHOLD=0.85;
	public static long DUPLICATE_BUFF_SIZE = 100 * 1024;	//排重用的 
	public static int PREPROCESS_THREAD_NUM = 1;	//
	public static int CONTAINER_CORE_SIZE = 1000;	//
	
	//容器大小

	public static String RAW_JSON_PATH="raw/";
	public static String DUP_JSON_PATH="dup/";
	
	public static final String ENCODING_DB = "utf-8";
	public static final String SEG_SEPARATOR="#";
	public static String SourceDBUrl;
	public static String SourceDBUser;
	public static String SourceDBPassword;

	public static String DBUrl;
	public static String DBUser;
	public static String DBPassword;
	
}
