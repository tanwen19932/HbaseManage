package main;

import edu.buaa.nlp.entity.util.HbaseUtil;
import edu.buaa.nlp.tw.common.DateUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DeleteHash {
	public static void main(String[] args) {
//		long start=-1262332800000L;
//		long end=1259596800000L;
		long start=1259596800000L;
		long end=1478503859000L;
		String prefixHash = "NewsDupHash";
		GregorianCalendar date = new GregorianCalendar();
		date.setTime(new java.util.Date(start));
		while (date.getTime().getTime() < (end ) ) {
			date.add(Calendar.MONTH, 1);
			String tableName=prefixHash+ DateUtil.getDateStr("yy-MM", date.getTime());
			System.out.println( "disable \""  + tableName + "\"");
			System.out.println( "drop \""  + tableName + "\"");
			System.out.println();
			try {
				HbaseUtil.configuration = HbaseUtil.configuration15;
				HbaseUtil.deleteTable(tableName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
