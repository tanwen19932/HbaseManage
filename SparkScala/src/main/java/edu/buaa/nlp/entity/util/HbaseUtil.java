package edu.buaa.nlp.entity.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wuxu
 * 
 */
public class HbaseUtil {
	public static Configuration configuration;
	public static Configuration configuration50;
	public static Configuration configuration15;
	
	static {
		System.out.println("初始化Hbase");
		System.setProperty("hadoop.home.dir", "/hadoop-common-2.2.0-bin-master");//Constants.WINUTIL_DIR);
		configuration15 = HBaseConfiguration.create();
		configuration50= new Configuration();
		configuration50.set( "hbase.zookeeper.quorum", "hbase.dnode8,hbase.dnode5,hbase.dnode6,hbase.dnode4,hbase.dnode7");
		configuration = configuration50;
	}
	public static Configuration getH50Conf(){
		 return configuration50;
	}
	public static Configuration getH15Conf(){
		return configuration15;
	}
	public static Configuration getConf() {
//		return getH50Conf();
		return configuration;
	}

	// 创建数据库表
	@SuppressWarnings("deprecation")
	public static void createTable(String tableName, String[] columnFamilys)
			throws Exception {
		// 新建一个数据库管理员
		HBaseAdmin hAdmin = new HBaseAdmin(configuration);
		if (hAdmin.tableExists(tableName)) {
			System.out.println("表 " + tableName + " 已存在！");
			System.exit(0);
		} else {
			// 新建一个students表的描述
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			// 在描述里添加列族
			for (String columnFamily : columnFamilys) {
				tableDesc.addFamily(new HColumnDescriptor(columnFamily));
			}
			// 根据配置好的描述建表
			hAdmin.createTable(tableDesc);
			System.out.println("创建表 " + tableName + " 成功!");
		}
	}

	// 删除数据库表
	@SuppressWarnings("deprecation")
	public static void deleteTable(String tableName) throws Exception {
		// 新建一个数据库管理员
		HBaseAdmin hAdmin = new HBaseAdmin(configuration);
		if (hAdmin.tableExists(tableName)) {
			// 关闭一个表
			try{
				hAdmin.disableTable(tableName);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
				hAdmin.deleteTable(tableName);
				System.out.println("删除表 " + tableName + " 成功！");
			} catch (Exception e) {
			}
		} else {
			System.out.println("删除的表 " + tableName + " 不存在！");
//			System.exit(0);
		}
	}

	// 删除一条(行)数据
	public static void delRow(String tableName, String rowKey) throws Exception {

		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);
			HTable table = (HTable) connection.getTable(TableName
					.valueOf(tableName));
			Delete del = new Delete(Bytes.toBytes(rowKey));
			table.delete(del);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 添加一条数据
	@SuppressWarnings("deprecation")
	public static void insertData(String tableName, String rowKey,
			String family, Map<String, String> map) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);
			HTable table = (HTable) connection.getTable(TableName
					.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
			for (Map.Entry<String, String> m : map.entrySet()) {
				put.add(Bytes.toBytes(family), Bytes.toBytes(m.getKey()),
						Bytes.toBytes(m.getValue()));
			}
			table.put(put);
			System.out.println("插入数据成功");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 添加一条数据多个内容
	@SuppressWarnings("deprecation")
	public static void insertMultiData(String tableName, String rowKey,
			String family, Map<String, String> map) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);
			HTable table = (HTable) connection.getTable(TableName
					.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
			for (Map.Entry<String, String> m : map.entrySet()) {
				put.add(Bytes.toBytes(family), Bytes.toBytes(m.getKey()),
						Bytes.toBytes(m.getValue()));
			}
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 根据rowkey从Hbase中获取新闻信息
	 */
	@SuppressWarnings("deprecation")
	public static void QueryByConditionRowkey(String tableName, String rowkey,
			Map<String, String> map) {
		Connection connection = null;
		try {

			connection = ConnectionFactory.createConnection(configuration);

			HTable table = (HTable) connection.getTable(TableName
					.valueOf(tableName));

			try {
				Get get = new Get(rowkey.getBytes());// 根据rowkey查询
				Result r = table.get(get);
				System.out.println("获得到rowkey:" + new String(r.getRow()));

				if (r.listCells() != null) {
					for (Cell cell : r.listCells()) {
						// String family = Bytes.toString(cell.getFamily());
						String qualifier = (Bytes.toString(cell.getQualifier()))
								.trim();
						System.out.println("value: "
								+ Bytes.toString(cell.getValue()));
						map.put(qualifier, Bytes.toString(cell.getValue()));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从Hbase中获取信息
	 */
	@SuppressWarnings("deprecation")
	public static List<Map<String, String>> QueryAllInfo(String tableName,
			Scan scan) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(configuration);
			HTable table = (HTable) connection.getTable(TableName
					.valueOf(tableName));

			try {
				if (scan == null)
					scan = new Scan();
				ResultScanner rs = table.getScanner(scan);
				List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
				for (Result r : rs) {
					if (r.listCells() != null) {
						Map<String, String> map = new HashMap<String, String>();
						String rowkey = "";
						for (Cell cell : r.listCells()) {
							rowkey = Bytes.toString(cell.getRow());
							String qualifier = (Bytes.toString(cell
									.getQualifier())).trim();
							// System.out.println(qualifier+"   "+Bytes.toString(cell.getValue()));
							map.put(qualifier, Bytes.toString(cell.getValue()));
						}
						map.put("rowkey", rowkey);
						lists.add(map);
					}

				}
				return lists;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static void getAllRecord (String tableName) {
        try{
             HTable table = new HTable(getConf(), tableName);
             Scan s = new Scan();
             ResultScanner ss = table.getScanner(s);
             int i =0;
             for(Result r:ss){
            	 i++;
                 for(KeyValue kv : r.raw()){
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                 }
             }
             System.out.println ("一共有 "+ i + " 条数据");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
	 public static void insertData (String tableName, String rowKey, String family, String qualifier, String value)
	            throws Exception{
	        try {
	            HTable table = new HTable(getConf(), tableName);
	            Put put = new Put(Bytes.toBytes(rowKey));
	            put.add(Bytes.toBytes(family),Bytes.toBytes(qualifier),Bytes.toBytes(value));
	            table.put(put);
	            System.out.println("insert recored " + rowKey + " to table " + tableName +" ok.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
