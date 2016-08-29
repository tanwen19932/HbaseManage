package tw.utils;

public class StringUtil {
	
	/**
	 * @param str
	 * @return 是否为空或者为"null"
	 */
	public static boolean isNull(String str) {
		return str==null || str.toLowerCase().trim().equals("null");
	}
	
	
	
}
