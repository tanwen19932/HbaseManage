package news.cisionNews.hbase;

public class CisionHttp extends CisionAPI {
	
	
	public static void main(String[] args) {
		CisionHttp cisionHttp2 = new CisionHttp();
		cisionHttp2.requestUrlPrefix = "http://wiseapiproxy.azurewebsites.net/xml/13b860b0-7c69-47fa-8a9f-482d638c32e0/TIME/1";
//		cisionHttp2.setDayLimit(400000);
		cisionHttp2.start();
	}
}
