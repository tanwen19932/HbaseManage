package edu.buaa.nlp.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class TranslateResultUtil {

	public static String getTranslateResult(String result) throws JSONException{
		JSONObject obj=null;
		try{
			obj=JSONObject.fromObject(result);
		}catch(JSONException e){
			throw e;
		}
		if(obj==null || obj.isNullObject()) return null;
		
		JSONArray arr=obj.getJSONArray("translation");
		if(arr==null || arr.isEmpty()) return null;
		JSONArray arr2=arr.getJSONObject(0).getJSONArray("translated");
		if(arr2==null || arr2.isEmpty()) return null;
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<arr2.size(); i++){
			JSONObject tran=arr2.getJSONObject(i);
			sb.append(tran.getString("text"));
		}
		return sb.toString();
		
//		String succeed = obj.getString("errorMessage");
//		String trans = null;
//		if(succeed.equalsIgnoreCase("succeed"))
//		{
//			trans = obj.getString("translation");
//		}
//		return trans;
	}
}
