package hbase.temp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.json.JSONObject;

import news.NewsDao;
import tw.utils.PropertiesUtil;

public class Test1000Hbase {
	static Vector<String> rowkeysList = new Vector<>();
	static HashSet<String> addedList = new HashSet<>(); 
	static Random random = new Random();
	static int count =0;
	public static void insertDir(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for( int i = 0 ; i < files.length; i++){
				try {
					if(files[i].isDirectory()){
						insertDir(files[i]);
						continue;
					}
					JSONObject jObject = getJsonObject(files[random.nextInt(files.length)]);
					rowkeysList.add(jObject.getString("id"));
					count++;
					if(count == 100) break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static JSONObject getJsonObject(File file) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader( new FileReader(file) );
		StringBuffer sb = new StringBuffer();
		String line = null;
		while( (line = bf.readLine())!= null ){
			sb.append(line);
		}
		JSONObject jo = new JSONObject( sb.toString() );
		return jo;
	}
	public static void main(String[] args) {
		File dir = new File("D:\\indexTestData");
		insertDir(dir);
	
		System.out.println(rowkeysList.size());
		
		NewsDao newsDao = new NewsDao();
		newsDao.get(rowkeysList);
		System.out.println(rowkeysList.size());
		newsDao.getByThreadN(rowkeysList, 80);
	}
}
