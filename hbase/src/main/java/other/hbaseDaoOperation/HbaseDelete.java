package other.hbaseDaoOperation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.hbase.client.Delete;

import hbase.hbaseUniversalDao.UniversalDaoM;
import hbase.util.HbaseUtil;
import tw.utils.FileUtils;

public class HbaseDelete {
	static Set<String> idSet = new HashSet<String>();
	static{
		String content;
		try {
			content = FileUtils.getFileStr("/home/_hadoop/tw/专题token.txt");
			for(String a : content.split("\n"))
				idSet.add(a);
			System.out.println(idSet.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) throws IOException {
//		HbaseUtil.getAllRecordToFile("ProductInfoV2", "E:/产品.txt", "P", "productName");
//		HbaseUtil.getAllRecordToFile("CompanyInfoV2", "E:/公司.txt", "C", "companyName");
		
		
		UniversalDaoM universalDao = new UniversalDaoM("Special", "S"){
			@Override
			public void handleAll(Map obj) {
				if(idSet.contains(obj.get("rowkey")) ){
					System.out.println( "检测到 不删除 rowkey : " + obj.get("rowkey"));
					return;
				}
				else{
					try {
						wTableLog[0].delete(new Delete( HbaseUtil.sToB(obj.get("rowkey").toString())) );
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}; 
		
		
//		FilterList filterList = new FilterList();
//		filterList.addFilter(
//				new SingleColumnValueFilter( sToB("I"), sToB("language"), CompareOp.EQUAL, sToB("English")));
		universalDao.getAll();	
	}
}
