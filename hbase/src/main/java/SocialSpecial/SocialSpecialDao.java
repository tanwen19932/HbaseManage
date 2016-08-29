package SocialSpecial;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.*;

import hbase.util.HbaseUtil;


public class SocialSpecialDao {
	
	static HTable table = null;
	static {
		try {
			table = new HTable(HbaseUtil.getConf(), "SocialSpecial");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//列簇
	static byte[] INFO_COLS = Bytes.toBytes("I");
	//列限定名
	

	public static SocialSpecial getSocialSpecialFromR(Result r) {
		SocialSpecial socialSpecial= new SocialSpecial( );
		socialSpecial.setSpecialId( Bytes.toString(r.getValue(INFO_COLS, Bytes.toBytes("specialId"))) );					
		socialSpecial.setTitle( Bytes.toString(r.getValue(INFO_COLS, Bytes.toBytes("title"))) );					
		socialSpecial.setDescription( Bytes.toString(r.getValue(INFO_COLS, Bytes.toBytes("description"))) );					
		socialSpecial.setKeyWord( Bytes.toString(r.getValue(INFO_COLS, Bytes.toBytes("keyWord"))) );					
		socialSpecial.setRegistTime( new Date( Bytes.toLong(r.getValue(INFO_COLS, Bytes.toBytes("registTime"))) ));					
		socialSpecial.setActiveTime( new Date( Bytes.toLong(r.getValue(INFO_COLS, Bytes.toBytes("activeTime"))) )  );					
		socialSpecial.setExpiredTime( new Date(Bytes.toLong(r.getValue(INFO_COLS, Bytes.toBytes("expiredTime"))) ) );					
		socialSpecial.setArrearageTime( new Date( Bytes.toLong(r.getValue(INFO_COLS, Bytes.toBytes("arrearageTime")))) );					
		socialSpecial.setDeleteTime( new Date( Bytes.toLong(r.getValue(INFO_COLS, Bytes.toBytes("deleteTime"))) ));					
		socialSpecial.setState( Bytes.toInt(r.getValue(INFO_COLS, Bytes.toBytes("state"))) );					
		socialSpecial.setAnalyzed( Bytes.toBoolean(r.getValue(INFO_COLS, Bytes.toBytes("analyzed"))) );					
		socialSpecial.setStartTime( Bytes.toLong(r.getValue(INFO_COLS, Bytes.toBytes("startTime"))) );					
		socialSpecial.setSpecialId( Bytes.toString(r.getValue(INFO_COLS, Bytes.toBytes("specialId"))) );					

		return socialSpecial;
	}
	
	
	
	
	public static void main(String[] agrs) throws Exception {
		
//		HbaseUtil.getAllRecord("NewsArticleBE");
		
		
/*		*//**按时间查数据**//*
		Date beginDate = new Date(2016-1900, 1, 1);
		Date endDate = new Date(2016-1900, 5, 1);

		SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		beginDate = dateFormat.parse("2016-04-18 00:00:00");
		endDate = dateFormat.parse("2016-04-19 23:00:00");
	
		System.out.println(beginDate.getTime());
		System.out.println(System.currentTimeMillis());
		System.out.println(endDate.getTime());
		getByDate(beginDate, endDate);*/
		
		
		/*List<String> tableList = new ArrayList<>();
		tableList.add("ariticle_0425");
		tableList.add("ariticle_0424");
		tableList.add("ariticle_0423");
		tableList.add("ariticle_0422");
		tableList.add("ariticle_0421");
		tableList.add("ariticle_0420");
		tableList.add("ariticle_0419");
		tableList.add("ariticle_0418");
		tableList.add("ariticle_0417");
		tableList.add("ariticle_0416");
		tableList.add("ariticle_0415");
		for(int i = 0 ; i < 1; i ++ ){
			InsertThread insertThread = new InsertThread(tableList.get(i)); 
			Thread i1 = new Thread(insertThread);
			i1.start();
		}*/

	}
	
	
	
	
	
}
