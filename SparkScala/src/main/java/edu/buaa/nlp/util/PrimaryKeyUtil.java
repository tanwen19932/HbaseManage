package edu.buaa.nlp.util;

public class PrimaryKeyUtil {

	private final static String str = "0123456789";
	private final static int pixLen = str.length();
	private static volatile int pixOne = 0;
	private static volatile int pixTwo = 0;
	private static volatile int pixThree = 0;
	private static volatile int pixFour = 0;
	
	public synchronized static long generateID() {
		StringBuilder sb = new StringBuilder();// 创建�?��StringBuilder
		sb.append(System.currentTimeMillis());// 先添加当前时间的毫秒�?
		pixFour++;
		if (pixFour == pixLen) {
			pixFour = 0;
			pixThree++;
			if (pixThree == pixLen) {
				pixThree = 0;
				pixTwo++;
				if (pixTwo == pixLen) {
					pixTwo = 0;
					pixOne++;
					if (pixOne == pixLen) {
						pixOne = 0;
					}
				}
			}
		}
		long result=Long.parseLong(sb.append(str.charAt(pixOne)).append(str.charAt(pixTwo)).append(str.charAt(pixThree)).append(str.charAt(pixFour)).toString());
		return result;
	}
	
	private synchronized static String generateStrID(String str) {
		int count = 0;
		String time = Long.toString(System.currentTimeMillis());
		time = time.substring(time.length() - 9, time.length());
		if (count > 99) {
			count = 0;
		} else {
			count++;
		}
		if (count < 10) {
			str += time + "0" + count;
		} else {
			str += time + count;
		}
		return str;
	}
	public static void main(String[] args) {
		for(int i=0; i<5000; i++){
//			System.out.println(generate() + ":"+System.currentTimeMillis());
		}
	}
}
