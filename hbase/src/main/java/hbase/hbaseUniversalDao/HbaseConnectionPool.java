package hbase.hbaseUniversalDao;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.hbase.client.HTable;
import hbase.util.HbaseUtil;

public class HbaseConnectionPool {
	protected int tableN = 20;
	protected int threadN = 20;
	private HTable[] tables;
	
	public HbaseConnectionPool(String tableName , int tableSize) {
		tableN = tableSize;
		threadN = tableSize;
		tables = new HTable[tableN];
		for (int i = 0; i < tables.length; i++) {
			try {
				tables[i] = new HTable(HbaseUtil.getConf(), tableName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public HTable get(){
		int temp = new Random().nextInt(tableN);
		HTable hTable = tables[temp];
		return hTable;
	}
	
}
