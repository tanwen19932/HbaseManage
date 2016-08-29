package hbase.temp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.AtomicDouble;

public class MT extends Thread{
	static AtomicInteger totalTime ;
	static {
		totalTime = new AtomicInteger(0);
	}

	public static void main(String[] args) throws MalformedURLException {

		int count = 0;
		long a = System.currentTimeMillis();
		
		while (count < 1000) {
			count++;
			MT aa  = new MT();
			aa.start();
			totalTime.incrementAndGet();
		}
		while(totalTime.get() != 0 ){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long b = System.currentTimeMillis();
		System.out.println("!!!!总共用时:" + (b-a)/1000 + "s");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String line, result;
		super.run();
		String text = "信息与通讯科技部资深分析师胡浩泳指出，随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口随着移动互联网的深入发展，手机浏览器作为入口的地位，越来越重要。从最近业内各方竞合关系来看，可以发现各参与者的思路日益开阔，已经由单纯的产品竞争上升到市场战略竞争，各主要玩家在渠道推广、支付创新等方面，都做得非常的出色，这很重要。另一方面，产品的根本，在于用户体验。只有在把握技术方向的同时，不断的推出用户认可的功能，才能在这个市场脱颖而出。";
		System.out.println(text.length());
		URL url;
		// URLEncoder.encode(
		StringBuffer pageBuffer = new StringBuffer();
		BufferedReader reader = null;

		try {
			url = new URL("http://translateport.yeekit.com:5700/translate?srcl=zh&tgtl=en&text="
					+ text);
			// System.out.println(url.toString());
			long start = System.currentTimeMillis();
			reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

			long end = System.currentTimeMillis();
			System.out.println("用时:" + (double) (end - start) / 1000 + "S");
			while ((line = reader.readLine()) != null)
				pageBuffer.append(line);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = pageBuffer.toString();
		System.out.println(result);
		totalTime.decrementAndGet();
	}
}
