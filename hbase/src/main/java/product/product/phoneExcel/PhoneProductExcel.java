package product.product.phoneExcel;

import static hbase.util.HbaseUtil.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.json.JSONObject;

import hbase.hbaseUniversalDao.UniversalDaoM;
import hbase.util.Murmurs;
import product.comment.excel.DirInserter;
import product.comment.excel.DropQualifier;
import product.comment.excel.StringFromExcel;
import tw.utils.FileUtils;

public class PhoneProductExcel {

	public static void main(String[] args) {
		try {
			DirInserter dirInserter = new DirInserter() {
				
				ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
				
				@Override
				public void insertFile(File file) {
					StringFromExcel stringFromExcel;
					try {
						stringFromExcel = new StringFromExcel(file.getAbsolutePath(),0);
						for (Iterator iterator = stringFromExcel.iterator(); iterator.hasNext();) {
							String[] values = (String[]) iterator.next();
							//天猫文件夹
//							values[0] = values[5]+" "+ values[6];
							String handled = DropQualifier.drop(values[0]).toLowerCase();
							if( map.containsKey( handled.toLowerCase() ) ){
								continue;
							}
							else {
								map.put(handled.toLowerCase(), values[1]);
								FileUtils.fileAppendMethod2("E:/手机_处理后.txt", handled+"\t=\t"+values[1]+"\r\n");
								FileUtils.fileAppendMethod2("E:/手机_处理前.txt", values[0]+"\t=\t"+values[1]+"\r\n");
							}
						}
//						String[] values = stringFromExcel.getString(1,0,1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				@Override
				public void init(){
					try{
						BufferedReader bf = new BufferedReader( new FileReader("E:/手机_处理后.txt"));
						String line ;
						while( (line = bf.readLine()) != null ){
							String[] paras = line.split("=");
							map.put( paras[0].trim(), paras[1].trim() ) ;
						}
					}catch (Exception e) {
					}
				}
			};
//			dirInserter.init();
//			dirInserter.insertDir("E:\\insert\\京东-手机");
//			dirInserter.insertDir("E:\\insert\\淘宝-手机");
//			dirInserter.insertDir("E:\\insert\\手机\\天猫");
			
			
			
			
			
//			Client client = ESClient.getClient("yeesightNew","192.168.55.45",9300);
			int count = 0;
//			BulkRequestBuilder bulkRequest = client.prepareBulk();
			try{
				BufferedReader bf = new BufferedReader( new FileReader("E:/杨楠_手机处理后.csv"));
				String line ;
				UniversalDaoM productDao = new UniversalDaoM("Product", "P"){;
					public void handleAll(java.util.Map obj) {
						try {
							wTableLog[1].delete(new Delete( sToB(obj.get("rowkey").toString())));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				FilterList filterList = new FilterList();
				filterList.addFilter(
						new SingleColumnValueFilter( sToB("P"), sToB("industryName"), CompareOp.EQUAL, sToB("手机")));
//				productDao.getAll(filterList);
				while( (line = bf.readLine()) != null ){
					String[] paras = line.split("=");
					JSONObject json = new JSONObject();
					String id = Murmurs.hashStr(paras[0].trim());
					json.put("productId", id);
					json.put("productName", paras[0].trim());
					json.put("industryName", "手机");
					json.put("companyName", paras[1].trim());
					json.put("analyzed", "0");
//					System.out.println(json.toString());
//					bulkRequest.add(client.prepareIndex("product", "product2", id).setSource(json.toString()));
//					bulkRequest.add(client.prepareDelete("product", "product2", id));
					
					/*添加Product*/ 
					productDao.insert( id,json);
					System.out.println("插入成功" + id);
//					count++;
//					if (count == 100) {
//						BulkResponse bulkResponse = bulkRequest.execute().actionGet();
//						bulkRequest = null;
//						bulkRequest = client.prepareBulk(); 
//						System.out.println(bulkResponse.getHeaders() +" "+ count);
//						count = 0;
//					}
				}
//				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
//				System.out.println(bulkResponse.getHeaders() +" " + count);
//				client.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
//			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
