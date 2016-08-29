package hbase.temp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.data.Json;
import org.json.JSONArray;
import org.json.JSONObject;

import hbase.util.HbaseUtil;
import news.NewsDao;

public class test {
	public static String convertUnicode(String ori){
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
 
        }
        return outBuffer.toString();
	}
	
	
	public static void findEles() {
		List<Map<String, String>> maps = new ArrayList<Map<String,String>>();
		maps = HbaseUtil.QueryAllInfo("ProductInfo", null);
		try {
			FileWriter fw = new FileWriter("D:/prodectsInfo.txt");
			for (Map<String, String> entry : maps) {
//				System.out.println(entry.get("proEles"));
				if(entry.get("proEles") != null && entry.get("proEles").length() > 2) {
					System.out.println(entry.get("rowkey"));
					System.out.println(entry.get("productName"));
					System.out.println(entry.get("proEles"));
					fw.write(entry.get("rowkey") + "\r\n");
					fw.write(entry.get("productName") + "\r\n\r\n");
//					fw.write(entry.get("proEles") + "\r\n" + "\r\n");
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException {
//		findEles();
		JSONObject jObject = new JSONObject("{\"domainName\":\"lmneuquen.com\",\"mediaNameEn\":\"Diario La Mañana Neuquén\",\"districtNameZh\":\"克劳塞镇\",\"mediaNameSrc\":\"Diario La Mañana Neuquén\",\"languageTname\":\"西班牙语\",\"countryNameZh\":\"阿根廷\",\"languageCode\":\"es\",\"districtNameEn\":\"Villa Krause \",\"mediaLevel\":\"4\",\"countryNameEn\":\"Argentina\"}");
		JSONArray ja = new JSONArray();
		ja.put(jObject);
		ja.put(jObject);
		JSONObject jo2 = ja.getJSONObject(0);
		jo2.put("domainName", "Testing!!!!!!!!!!!!!!!!!!!!!!!!!!");
		JSONObject jo3 = ja.getJSONObject(0);
		System.out.println(jo3.get("domainName"));
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date date = new Date();
//		java.sql.Date date2 = (java.sql.Date.valueOf(simpleDateFormat.format(date))) ;
//		System.out.println(date2);
//		String aString = convertUnicode("[{\"title\":\"#\u52a0关注领红\u5305#中了中了\uff0c");
//		System.out.println(aString +"!!!!!");
//		Map<String , Integer> sourceCount = new HashMap<>();
//		if( sourceCount.get("hm")==null ){
//			System.out.println("??");
//		}
//		File file = new File(NewsDao.class.getResource("/").getPath()+"count"+".txt");
//		BufferedWriter bf = new BufferedWriter(new FileWriter(file));
//		bf.write("????????💘??????1");
//		bf.newLine();
//		bf.write("???????????????1");
//		bf.newLine();
//		bf.write("???????????????1");
//		bf.close();
	}
	
}
