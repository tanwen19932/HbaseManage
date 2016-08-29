package hbase.temp;

import org.json.JSONObject;

public class TestJson {
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		String zhongyi = "\"中译\"";
		System.out.println(zhongyi);
		jsonObject.append("this", zhongyi);
		System.out.println( jsonObject.toString() );
	}
}
