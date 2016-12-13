package edu.buaa.nlp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 发送HTTP post 请求
 * @author Vincent
 *
 */
public class PostConnection {

	//private URLConnection post;
	private HttpURLConnection post;
	private PrintWriter postOut;
	private String urlStr;

	/**
	 * @param url 连接地址
	 */
	public PostConnection(String url) {
		this.urlStr = url;
		postConn();
	}

	private void postConn() {
		try {
			URL realUrl = new URL(urlStr);
			post = (HttpURLConnection) realUrl.openConnection();
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 设置通用的请求属性
		post.setRequestProperty("accept", "*/*");
		post.setRequestProperty("connection", "Keep-Alive");
		post.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		try {
			post.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			post.setDoOutput(true);
			post.setDoInput(true);						
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

/*		Map<String, List<String>> map=post.getRequestProperties();
		for(String key:map.keySet()){
			System.out.println(map.get(key));
		}*/
/*		Map<String, List<String>> head = post.getHeaderFields();
        for (String key : head.keySet()) {
            System.out.println(key + "--->" + head.get(key));
        }*/
	}
	
	/**
	 * @param params 请求参数，格式：name1=value1&name2=value2
	 * @return
	 * @throws IOException 
	 */
	public String postResult(String params) throws IOException{
		if((params == null) || (params.trim().isEmpty()))
			return "";
		
		// 获取URLConnection对象对应的输出流
		try {
			postOut = new PrintWriter(post.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postOut.print(params);
		// flush输出流的缓冲
		postOut.flush();
		// 定义BufferedReader输入流来读取URL的响应
		BufferedReader in=null;
		StringBuffer result=new StringBuffer();
		try {
			in = new BufferedReader(new InputStreamReader(post.getInputStream()));
			String line="";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		close();
		return result.toString();
	}
	 
	public String translate(String text) {
		return translate(text, "en", "zh");
	}
	
	public String translate(String text, String srcl, String tgtl) {
		try {
			JSONObject json = new JSONObject();
			json.put("srcl", srcl);
			json.put("tgtl", tgtl);
			json.put("text", text);
			String postParam = json.toString();
			return postResult(postParam);
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
	
	/**
	 * 关闭输出流
	 */
	private void close(){
		if(postOut!=null)
			postOut.close();
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
		/*
		for(int i=0;i<triedNum;i++)
		{
			try
			{
				PostConnection post=new PostConnection(url);
				
				String result = post.postResult("text="+text+param);				
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
		*/
		
	}
	
	public static String PostConnectionForTimes(String url,String text,String srcLang,String tgtLang,int triedNum)
	{
		for(int i=0;i<triedNum;i++)
		{
			try
			{
				PostConnection post=new PostConnection(url);
				String result = post.translate(text, srcLang, tgtLang);		
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
	
	
	public static void main(String[] args) {
		//PostConnection connection=new PostConnection("http://translateport.yeekit.com/translate");
				
		System.out.println(PostConnectionForTimes("http://43.240.136.156:4050/translate","Basketball recruiting: Kenwood's Zion Morgan commits to UNLV",3));

		
/*		for(int j=0;j<3;j++)
		{
			try
			{
				PostConnection post=new PostConnection(Constants.TRANSLATE_SERVER_URL);
				String r=post.postResult("text=中国&srcl=zh&tgtl=en");
				String result = TranslateResultUtil.getTranslateResult(r);
				System.out.println(result);
				Thread.sleep(200);
				break;
			}
			catch(Exception e)
			{
				continue;
			}
		}*/
		
//		//String r=connection.postResult("text=中国&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true");
//		String r=connection.postResult("text=Trump’s July just got far more interesting&srcl=en&tgtl=zh");
//		System.out.println(Thread.currentThread().getName()+":"+getTranslateResult(r));
/*		Thread thread1=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					PostConnection connection=new PostConnection("http://translateport.yeekit.com/translate");
					String r=connection.postResult("text=中国&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true");
					System.out.println(Thread.currentThread().getName()+":"+getTranslateResult(r));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "thread1");
		Thread thread2=new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					PostConnection connection=new PostConnection("http://translateport.yeekit.com/translate");
					String r=connection.postResult("text=午餐&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true");
					System.out.println(Thread.currentThread().getName()+":"+getTranslateResult(r));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "thread2");
		thread1.start();
		thread2.start();*/
	}
	
	private static String getTranslateResult(String result){
		JSONObject obj=JSONObject.fromObject(result);
		if(obj==null || obj.isNullObject()) return null;
		System.out.println(obj);
		
/*		String succeed = obj.getString("errorMessage");
		String trans = null;
		if(succeed.equalsIgnoreCase("succeed"))
		{
			trans = obj.getString("translation");
		}*/
		//*
		JSONArray arr=obj.getJSONArray("translation");
		if(arr==null || arr.isEmpty()) return null;
		JSONArray arr2=arr.getJSONObject(0).getJSONArray("translated");
		if(arr2==null || arr2.isEmpty()) return null;
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<arr2.size(); i++){
			JSONObject tran=arr2.getJSONObject(i);
			sb.append(tran.getString("text"));
		}
		//*/
		
		return sb.toString();
	}
}
