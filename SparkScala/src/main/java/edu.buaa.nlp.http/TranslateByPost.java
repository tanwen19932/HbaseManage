/**
 * 
 */
package edu.buaa.nlp.http;

import java.io.IOException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 * Date: 2016.6.4
 * 通过Post方式翻译文本
 */
public class TranslateByPost {

	public TranslateByPost()
	{
		
	}
	
	
	public static String translate(PostConnection postConn,String text) {
		return translate(postConn,text, "en", "zh");
	}
	
	public static String translate(PostConnection postConn,String text, String srcl, String tgtl) {
		try {
			JSONObject json = new JSONObject();
			json.put("srcl", srcl);
			json.put("tgtl", tgtl);
			json.put("text", text);
			String postParam = json.toString();
			return postConn.postResult(postParam);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * 多次调用PostConnection，直至成功范围
	 * @param text
	 * @param triedNum
	 * @return
	 */
	public static String PostConnectionForTimes(String url,String text,int triedNum)
	{
		return PostConnectionForTimes(url,text,"en","zh",triedNum);
	}
	
	public static String PostConnectionForTimes(String url,String text,String srcLang,String tgtLang,int triedNum)
	{
		for(int i=0;i<triedNum;i++)
		{
			try
			{
				PostConnection post=new PostConnection(url);
				String result = translate(post,text, srcLang, tgtLang);		
				result = TranslateResultUtil.getTranslateResult(result);
				return result;				
			}
			catch(Exception e)
			{
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//translate_server_url=http://43.240.136.156:4050/translate
		System.out.println(PostConnectionForTimes("http://43.240.136.156:4050/translate","China",3));
	}

}
