package hbase.temp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UuidUtil {
	public static Object UTIL_OJHID = new Object();
	public static int UTIL_IJHIDCOUNT = 1000;

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 
	 * @return
	 */
	public static String getOrderID() {
		  
		synchronized (UTIL_OJHID) {
			if (UTIL_IJHIDCOUNT >= 9999) {
				UTIL_IJHIDCOUNT = 1000;
			}
			StringBuffer sb = new StringBuffer(20);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			sb.append(format.format(new java.util.Date()));
			sb.append(UTIL_IJHIDCOUNT);
			try {
				
				Thread.sleep(1);// 等待1毫秒
				UTIL_IJHIDCOUNT = UTIL_IJHIDCOUNT + 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		
		
	}
}
