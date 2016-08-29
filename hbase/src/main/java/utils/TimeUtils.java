package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
	private static final String[] WEEKDAYS = { "", "一", "二", "三", "四", "五", "六", "日" };

	private static final String[] MONTHS = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

	private static final String[] AMPM = { "上午", "下午" };

	public static long getTime(String timeString) {
		List<SimpleDateFormat> sdfList = new ArrayList<>();
		
		sdfList.add( new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒") );
		sdfList.add( new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss") );
		sdfList.add( new SimpleDateFormat("yyyy年MM月dd日 HH时") );
		sdfList.add( new SimpleDateFormat("yyyy年MM月dd日") );
		sdfList.add( new SimpleDateFormat("yyyy年MM月") );
		
		sdfList.add( new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss") );
		sdfList.add( new SimpleDateFormat("yyyy/MM/dd/ HH:mm") );
		sdfList.add( new SimpleDateFormat("yyyy/MM/dd/") );
		sdfList.add( new SimpleDateFormat("yyyy/MM/dd") );
		sdfList.add( new SimpleDateFormat("yyyy/MM") );
		
		sdfList.add( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") );
		sdfList.add( new SimpleDateFormat("yyyy-MM-dd") );
		sdfList.add( new SimpleDateFormat("yyyy-MM") );
		
		
		sdfList.add( new SimpleDateFormat("HH:mm:ss yyyy/MM/dd/") );
		
//		sdfList.add( new SimpleDateFormat("一年中的第 D 天 一年中第w个星期 一月中第W个星期 在一天中k时 z ZZZ时区 EEE MMM") );
		for( int i =0 ;i<sdfList.size() ; i++ ){
			try{
				Date date  = sdfList.get(i).parse(timeString);
				return date.getTime();
			}catch (Exception e){
				continue;
			}
		}
		return -1;
	}

	public static long getWeiboTimeL(String timeStr) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = sdf.parse(timeStr);
		return date.getTime();
	}
	
	public static String getStandardTimeS(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return sdf.format(new Date(date));
	}
	
	
	public static void main(String[] args) throws ParseException {
		TimeUtils.getWeiboTimeL("Tue May 31 17:46:55 +0800 2011");
		System.out.println(TimeUtils.getTime("2015/01/01"));
	}
}
