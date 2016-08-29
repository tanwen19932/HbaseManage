package hbase.hbaseUniversalDao;

import static hbase.util.HbaseUtil.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;

import hbase.util.HbaseUtil;
import news.NewsDao;
import tw.utils.ReflectUtil;

/**
 * Hbase通用Dao 以Map为对象
 * 
 * @version 0.1
 * @author TW
 *
 */
@SuppressWarnings("deprecation")
public class UniversalDaoM {
	private String family = null;
	protected static Log LOG = LogFactory.getLog(UniversalDaoM.class);
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
	public UniversalDaoM(String tableName, String family) {
		synchronized (isInit) {
			if (isInit)
				isInit = true;
		}
		this.family = family;
		System.out.println(System.getProperty("user.dir"));

		wTableLog = new HTable[tableN];
		try {
			for (int i = 0; i < tableN; i++) {
				wTableLog[i] = new HTable(HbaseUtil.getConf(), tableName);
//				wTableLog[i].setWriteBufferSize(5 * 1024 * 1024); // 5MB
//				wTableLog[i].setAutoFlush(false);
			}
			// putList = Collections.synchronizedList(new LinkedList<>());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addPut(Put put, String family, String quaifier, String value) {
		if (value != null) {
			put.add(Bytes.toBytes(family), Bytes.toBytes(quaifier), Bytes.toBytes(value));
		}
	}

	public void addPut(Put put, String family, String quaifier, int value) {
		put.add(Bytes.toBytes(family), Bytes.toBytes(quaifier), Bytes.toBytes(value));
	}

	public void addPut(Put put, String family, String quaifier, long value) {
		put.add(Bytes.toBytes(family), Bytes.toBytes(quaifier), Bytes.toBytes(value));
	}

	public void addPut(Put put, String family, String quaifier, boolean value) {
		put.add(Bytes.toBytes(family), Bytes.toBytes(quaifier), Bytes.toBytes(value));
	}

	/**
	 * getAll为一个操作 </br>
	 * 
	 * @warn 需要复写handleAll
	 * @author TW
	 * @param  过滤器 可以为null
	 * @throws IOException
	 */
	public void getAll(FilterList filterList) throws IOException {
		Scan scan = new Scan();
		if(filterList!=null){
			scan.setFilter(filterList);
		}
		ResultScanner rs = wTableLog[random.nextInt(tableN)].getScanner(scan);
		for (Result r : rs) {
			Map<String, String> obj = getMapFromR(r);
			handleAll(obj);
		}
	}
	
	/**
	 * @throws IOException
	 */
	public void getAll() throws IOException {
		Scan scan = new Scan();
		ResultScanner rs = wTableLog[random.nextInt(tableN)].getScanner(scan);
		for (Result r : rs) {
			Map<String, String> obj = getMapFromR(r);
			handleAll(obj);
		}
	}
	
	
	
	public void handleAll(Map obj) {
	}

	public Map<String, String> getMapFromR(Result r) {
		Map<String, String> map = new HashMap();
		map.put("rowkey", bToS(r.getRow()));
		for (Cell kv : r.rawCells()) {
			map.put(bToS(kv.getQualifier()), bToS(kv.getValue()));
		}
		return map;
		// System.out.println(news.getId() + " " + news.getMediaNameZh() + " " +
		// news.getTitleSrc() + " " + news.getCreated() + news.getTextSrc());
	}

	public void insert(Map<String, String> map) throws IOException {
		byte[] rowkey;

		rowkey = Bytes.toBytes((String) map.get("rowkey"));
		Put put = new Put(rowkey);
		for (Entry<String, String> entry : map.entrySet()) {
			if (entry.getKey().equals("rowkey")) {
				continue;
			}
			put.add(sToB(family), sToB(entry.getKey()), sToB(entry.getValue()));
		}
		wTableLog[random.nextInt(tableN)].put(put);
	}
	
	public void insert( String rowkey ,JSONObject json) throws Exception {
		Put put = new Put(sToB(rowkey));
		Iterator iterator = json.keys();
		while (iterator.hasNext()) {
			
			String key = (String) iterator.next();
			if( key.equals("rowkey") )
				continue;
			String value = json.getString(key);
			put.addColumn( sToB(family), sToB(key), sToB(value));
		}
		wTableLog[random.nextInt(tableN)].put(put);
	}
	
	public void insert(JSONObject json) throws Exception {
		// TODO Auto-generated method stub
		byte[] rowkey;
		if(json.has("rowkey")){
			rowkey = Bytes.toBytes((String) json.get("rowkey"));
		}
		else if(json.has("id")){
			rowkey = Bytes.toBytes((String) json.get("id"));
		}
		else throw new Exception("Json中未包含rowkey 或者 id");
		// 澧炲姞涓�鏉¤褰�
		Put put = new Put(rowkey);

		Iterator iterator = json.keys();
		while (iterator.hasNext()) {
			
			String key = (String) iterator.next();
			if( key.equals("rowkey") )
				continue;
			String value = json.getString(key);
			put.addColumn(sToB(family), sToB(key), sToB(value));
		}
		wTableLog[random.nextInt(tableN)].put(put);
	}

}
