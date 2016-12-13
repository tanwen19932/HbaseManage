package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import edu.buaa.nlp.http.PostConnection;

import net.sf.json.JSONObject;

public class TranslateAPITest {

	public static void main(String[] args) {
//		String result=sendGet("http://translateport.yeekit.com/translate", "text=围绕中国的“GDP平减指数”（对名义GDP进行通胀调整，得出政治上敏感的实际GDP增长率），人们抱有种种疑虑。GDP平减指数应该是最广义的通胀指标，涵盖所有商品和服务（包括非消费品）的价格变化。但是，如果第四季度的平减指数大幅偏离人们比较熟悉的中国通胀指标，投资者将怀疑中国国家统计局在数据上做手脚，因而把名义增长率视为反映经济状况的更可靠指标。&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true");
		String res2=sendPost("http://translateport.yeekit.com/translate", "text=Un año sin ‘Jimmy’, un caso resuelto y atascado en la Justicia | España | EL PA&Iacute;S&srcl=en&tgtl=zh&detoken=true&nBestSize=5&align=true");
//		JSONObject jsonObject=JSONObject.fromObject(result);
//		System.out.println(jsonObject.getJSONArray("translation").getJSONObject(0).getJSONArray("translated").getJSONObject(0).get("text"));
//		System.out.println(JSONObject.fromObject(res2).getJSONArray("translation").getJSONObject(0).getJSONArray("translated").getJSONObject(0).get("text"));
		System.out.println(res2.toString());
	}
	
	
	private static void concurrTest() throws IOException{
		int N = 1000;
		// single connection
		String url="http://translateport.yeekit.com/translate";
		long t1 = System.currentTimeMillis();
		PostConnection postConnection=new PostConnection(url);
		for(int i=0; i<N; i++){
			String r=postConnection.postResult("text=中国&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true");
			System.out.println(JSONObject.fromObject(r).getJSONArray("translation").getJSONObject(0).getJSONArray("translated").getJSONObject(0).get("text"));
		}
		long t2 = System.currentTimeMillis();
		
		long t3 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		System.out.println(t3 - t2);
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * @param url	发送请求的URL
     * @param param	请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
/*            // 遍历所有的响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }finally {	// 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
}
