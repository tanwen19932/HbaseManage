package other.test;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import tw.utils.FileUtils;

public class RankTestAPI {
public static void main(String[] args) throws IOException {
	String json = FileUtils.getFileStr("E:\\insert\\RankAPI\\test.json");
	JSONObject jObject =  new JSONObject(json);
	JSONArray jArray = jObject.getJSONArray("data");
	for (int i = 0; i < jArray.length(); i++) {
		JSONObject temp = jArray.getJSONObject(i);
		JSONObject content = temp.getJSONObject("article");
		
		System.out.println("--------------------");
		System.out.println(content.get("content"));
		System.out.println("--------------------");
		
	}
}
}
