package other.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class fakeTest {

	public static void main(String args[]) {

//		for (int i = 0; i < 2000; i++) {
//
//			String rowkey;
//			String str = String
//					.valueOf((int) (System.currentTimeMillis() % 20));
//			if (str.length() == 2) {
//				rowkey = str;
//			} else if (str.length() == 1)
//				rowkey = "0" + str;
//			else {
//				rowkey = "00";
//			}
//			System.out.println("rowkey: " + rowkey);
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(new Date(1450320660*1000L)));
		
//		String aString = "中国";
//		changeStr(aString);
//		System.out.println(aString);
		
//		Map<String, String >  map = new HashMap<>();
//		map.put("made", "temp");
//		System.out.println(map.get("made"));
//		
//		String a= "涉　县";
//		System.out.println((int)a.charAt(1));
//		System.out.println(a.replaceAll("\\s", ""));
		Date date = new Date();
		System.out.println(date.getYear()+1900);
		
	}
	public static void changeStr(String a) {
		a += "AA";
		System.out.println( a );
	}
}