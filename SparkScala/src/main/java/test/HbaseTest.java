package test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.PropertyConfigurator;

import edu.buaa.nlp.util.Constants;

public class HbaseTest {

	static Configuration conf = null;

	/**
	 * 初始化配置
	 */
	static {
		System.setProperty("hadoop.home.dir",Constants.WINUTIL_DIR);
		conf = HBaseConfiguration.create();
//		conf.set("hbase.master.port", "43.240.136.164");
		System.out.println("create configuration");
		System.out.println(conf);
//		System.out.println(conf.get("hbase.master"));
	}

	public static void showAll(String tableName) throws Exception {

		HTable h = new HTable(conf, tableName);

		Scan scan = new Scan();
		// 扫描特定区间
		// Scan scan=new Scan(Bytes.toBytes("开始行号"),Bytes.toBytes("结束行号"));
		ResultScanner scanner = h.getScanner(scan);
		for (Result r : scanner) {
			System.out.println("==================================");
			for (KeyValue k : r.raw()) {

				System.out.println("行号:  " + Bytes.toStringBinary(k.getRow()));
				System.out.println("时间戳:  " + k.getTimestamp());
				System.out.println("列簇:  "
						+ Bytes.toStringBinary(k.getFamily()));
				System.out.println("列:  "
						+ Bytes.toStringBinary(k.getQualifier()));
				// if(Bytes.toStringBinary(k.getQualifier()).equals("myage")){
				// System.out.println("值:  "+Bytes.toInt(k.getValue()));
				// }else{
				String ss = Bytes.toString(k.getValue());
				System.out.println("值:  " + ss);
				// }

			}
		}
		h.close();

	}

	/**
	 * 创建表操作
	 * 
	 * @throws IOException
	 */
	public void createTable(String tablename, String[] cfs) throws IOException {
		System.out.println("start");
		Connection connection = ConnectionFactory.createConnection(conf);
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		/*
		 * if (admin.tableExists(tablename)) { System.out.println("表已经存在！"); }
		 * else { //若该表不存在则新建
		 */
		HTableDescriptor tableDesc = new HTableDescriptor(
				TableName.valueOf(tablename));
		System.out.println("start creat table");
		for (int i = 0; i < cfs.length; i++) {
			tableDesc.addFamily(new HColumnDescriptor(cfs[i]));
		}
		admin.createTable(tableDesc);
		System.out.println("表创建成功！");
		// }
		admin.close();
	}

	public static void insertData(String tableName) {
		System.out.println("start insert data ......");
		HTablePool pool = new HTablePool(conf, 1000);
		HTable table = (HTable) pool.getTable(tableName);
		Put put = new Put("112233bbbcccc".getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
		put.add("column1".getBytes(), null, "aaa".getBytes());// 本行数据的第一列
		put.add("column2".getBytes(), null, "bbb".getBytes());// 本行数据的第三列
		put.add("column3".getBytes(), null, "ccc".getBytes());// 本行数据的第三列
		try {
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end insert data ......");
	}

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		/*HbaseTest ho = new HbaseTest();
		String[] cfs = new String[2];
		cfs[0] = "age";
		cfs[1] = "sex";
		ho.createTable("students", cfs);*/
		showAll("member");
	}

}
