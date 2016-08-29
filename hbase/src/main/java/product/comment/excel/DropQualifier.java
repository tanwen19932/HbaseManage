package product.comment.excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropQualifier {
	private static final String DICPATH = "D:/HanLP/qualifiers.txt";
	static List<String> qualifiers = new LinkedList<>();
	static {
		getQualifiers();
	}
	public static String drop(String str){
		if(str.length()<10)
			return str;
		for(String qualifier : qualifiers){
//			System.out.println(qualifier);
			Pattern pattern = Pattern.compile(qualifier,Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			while( matcher.find() ){
				if(matcher.equals("魅蓝"))
					continue;
				str = matcher.replaceAll("").trim();
//				System.out.println( str +"\t"+"regex ："+qualifier );
			}
		}
		return str.trim() ;
	}
	
//	public static String checkDrop(String ){
//		
//	}
	
	private static void getQualifiers() {
		File file = new File(DICPATH);
		try{
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line ;
			while( (line = bf.readLine()) != null ){
				qualifiers.add(line.trim());
			}
		}catch (Exception e){
		}
	}
	
	public static void main(String[] args) {
		String a = DropQualifier.drop("MX4PRO");
		System.out.println( a );
	}
	
}
