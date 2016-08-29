package monitor.hbaseMonitorHTTP;

import java.net.MalformedURLException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import org.json.JSONArray;
import org.json.JSONObject;

import tw.utils.HttpUtil;
@WebService(endpointInterface = "hbaseMonitor_http.HbaseMetricsI")
@SOAPBinding(style = Style.RPC)
public class HbaseMetricsImpl implements HbaseMetricsI{
	String jsonStr = null;
	static final String ERROR = "{\"beans\" : [ ]}";
		
	public HbaseMetricsImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getStr(String json) {
		try {
			JSONObject jo = new JSONObject(json);
			String qry = null;
			String ip = null;
			String name = null;
			if(jo.has("qry")){
				qry = jo.getString("qry");
			}
			if(jo.has("name")){
				name = jo.getString("name");
			}
			
			ip = jo.getString("ip");
			
			String queryUrl = null;

			if (qry != null && !qry.equals("")) {
				if (qry.equals("Memory") || qry.equals("OperatingSystem")) {
					queryUrl = "http://" + ip + "/jmx?qry=java.lang:type=" + qry;
				}
				else 
				queryUrl = "http://" + ip + "/jmx?qry=Hadoop:service=HBase,name=" + name + ",sub="+qry;
			} else
				queryUrl = "http://" + ip + "/jmx";
			System.out.println("request URL : " + queryUrl);
			return HttpUtil.getHttpContent(queryUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ERROR;
	}
	public static void main(String[] args) {
		final String SUCCESS = "发布服务成功";
		
		String publishAddr = "http://192.168.9.126:8101/hbaseMetrics?wsdl";	
		Endpoint.publish(publishAddr, new HbaseMetricsImpl());
		System.out.println(SUCCESS);
		
		HbaseMetricsImpl hbaseMetric = new HbaseMetricsImpl();
		String jo1 = "{\"ip\":\"192.168.55.95:60010\"}";
		JSONObject jo = new JSONObject( hbaseMetric.getStr(jo1) );
		JSONArray ja = jo.getJSONArray("beans");
		System.out.println(jo);
	}
	

}
