package news.hongmaiNews.hbase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharsetUtil {
	public static boolean check(String text) throws Exception{
		Pattern pattern = Pattern.compile("(勪|閬╂|鏂|鎴|鐨|槸|琚|戜|鏄|鏈|杩|鏉|鍒|鏃|鍦|鐢|鍥|鐫|閭|鍜|鍑|涔|閲|鍚|鑷|浼|姹|闂|曐|鎯|閿|爜|鏁|鏅|椾|鍏|悆|鈥)");
		Matcher m = pattern.matcher(text);
		
		int matcherCount = 0;
		StringBuffer luanMa = new StringBuffer();
		while(m.find()){
			matcherCount++;
			luanMa.append( m.group() );
			if(matcherCount > 5 ) break;
		}
		if( matcherCount > 5) {
			System.err.println( luanMa );
			return true;
		}
		return false;
	}
}
