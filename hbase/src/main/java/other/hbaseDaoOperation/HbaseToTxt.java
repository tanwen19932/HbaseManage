package other.hbaseDaoOperation;

import static hbase.util.HbaseUtil.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

import hbase.hbaseUniversalDao.UniversalDao;
import hbase.hbaseUniversalDao.UniversalDaoM;
import hbase.util.HbaseUtil;
import tw.utils.FileUtils;

public class HbaseToTxt {
	
	public static void main(String[] args) throws IOException {
//		HbaseUtil.getAllRecordToFile("ProductInfoV2", "E:/产品.txt", "P", "productName");
//		HbaseUtil.getAllRecordToFile("CompanyInfoV2", "E:/公司.txt", "C", "companyName");
		
		UniversalDaoM universalDao = new UniversalDaoM("Cision", "I"){
			AtomicInteger count = new AtomicInteger();
			@Override
			public void handleAll(Map obj) {
				// TODO Auto-generated method stub
				String line = count.incrementAndGet()+"- " +obj.get("text").toString().replaceAll("\\s", " ")+ "\r\n";
//				System.out.println(line);
				FileUtils.fileAppendMethod2("E:/twitter.txt", line);
			}
		}; 
		FilterList filterList = new FilterList();
		filterList.addFilter(
				new SingleColumnValueFilter( sToB("I"), sToB("language"), CompareOp.EQUAL, sToB("English")));
		universalDao.getAll(filterList);	
	}
}
