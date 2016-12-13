package edu.buaa.nlp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 发送HTTP get 请求
 * @author Vincent
 *
 */
public class GetConnection {

	private URLConnection get;
	private String urlStr;

	/**
	 * @param url 连接地址
	 */
	public GetConnection(String url) {
		this.urlStr = url;
		getConn();
	}

	private void getConn() {
		try {
			URL realUrl = new URL(urlStr);
			get = realUrl.openConnection();
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 设置通用的请求属性
		get.setRequestProperty("accept", "*/*");
		get.setRequestProperty("connection", "Keep-Alive");
		get.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		try {
			get.connect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @param params 请求参数，格式：name1=value1&name2=value2
	 * @return
	 */
	public String postResult(String params){
		// 定义BufferedReader输入流来读取URL的响应
		BufferedReader in=null;
		StringBuffer result=new StringBuffer();
		try {
			in = new BufferedReader(new InputStreamReader(get.getInputStream()));
			String line="";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
}
