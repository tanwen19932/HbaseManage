package other.hbaseUniversalDao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import hbase.util.HbaseUtil;
import news.NewsDao;
import tw.utils.ReflectUtil;

/**
 * Hbase通用Dao 以Object为对象
 * @version 0.1
 * @author TW
 *
 */
@SuppressWarnings("deprecation")
public class UniversalDao extends MyDao{
	private String family = null;
	Object object= null;
	protected static Log LOG = LogFactory.getLog(UniversalDao.class);
	protected static final int tableN = 20;
	static final int threadN = 20;
	static Boolean isInit = false;
	static boolean isWrite = true;
	
	protected static HTable[] wTableLog;
	protected static Random random = new Random();
	
	/**
	 * 
	 * @param tableName
	 * @param family
	 * @param obj类
	 */
	public  UniversalDao(String tableName , String family , Object obj) {
		synchronized (isInit) {
			if(isInit)
				isInit = true;
		}
		this.family = family; 
		this.object = obj;
		System.out.println(System.getProperty("user.dir"));
		
		wTableLog = new HTable[tableN];
		try {
			for (int i = 0; i < tableN; i++) {
				wTableLog[i] = new HTable(HbaseUtil.getConf(), tableName);
				wTableLog[i].setWriteBufferSize(5 * 1024 * 1024); // 5MB
				wTableLog[i].setAutoFlush(false);
			}
			// putList = Collections.synchronizedList(new LinkedList<>());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addPut(Put put, String family , String quaifier , String value) {
		if(value != null){
			put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
		}
	}
	public void addPut(Put put, String family , String quaifier , int value) {
		put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
	}
	public void addPut(Put put, String family , String quaifier , long value) {
		put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
	}
	public void addPut(Put put, String family , String quaifier , boolean value) {
		put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
	}
	
	
	
	/**
	 * getAll为一个操作
	 * </br>
	 * @warn 需要复写handleAll
	 * @author TW
	 * @throws IOException
	 */
	public void getAll() throws IOException {
		Scan scan = new Scan();
		ResultScanner rs = wTableLog[random.nextInt(tableN)].getScanner(scan);
		for(Result r : rs){
			Object obj = getObjFromR(r);
			handleAll(obj);
		}
	}
	
	public void handleAll(Object obj){
		
	}
	
	public Object getObjFromR(Result r ) {
		Object obj = null;
		Field[] fields = ReflectUtil.getObjFields(object);
		try {
			obj = ReflectUtil.constructor( object ) ;
			for(int i = 0 ; i < fields.length ; i++){
				ReflectUtil.invokeObjSetMethod(obj, fields[i], r.getValue(Bytes.toBytes(family), Bytes.toBytes( fields[i].getName()) ) );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
//		System.out.println(news.getId() + " " + news.getMediaNameZh() + " " + news.getTitleSrc() + " " + news.getCreated() + news.getTextSrc());
	}
	
	

	@Override
	public Object getObjFromR(ResultSet r ) {
		// TODO Auto-generated method stub
		Object obj = null;
		Field[] fields = ReflectUtil.getObjFields(object);
		try {
			obj = ReflectUtil.constructor( object ) ;
			for(int i = 0 ; i < fields.length ; i++){
				ReflectUtil.invokeObjSetMethod(obj, fields[i], r.getString(i+1));
//				System.out.println(r.getString(i+1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(news.getId() + " " + news.getMediaNameZh() + " " + news.getTitleSrc() + " " + news.getCreated() + news.getTextSrc());

		return obj;
	}

	@Override
	public void insert(Object obj) {
		// TODO Auto-generated method stub
			Field[] fields = ReflectUtil.getObjFields(obj);
			try {
				byte[] rowkey;
				
				rowkey = Bytes.toBytes( String.valueOf(ReflectUtil.invokeObjGetMethod(obj, fields[0].getName())) );
				//澧炲姞涓�鏉¤褰�
				Put put = new Put(rowkey);
				for(int i = 0 ; i < fields.length ; i++){
					addPut( put,family,fields[i].getName(), String.valueOf( ReflectUtil.invokeObjGetMethod(obj, fields[i].getName())) );
				}
				wTableLog[random.nextInt(tableN)].put(put);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
