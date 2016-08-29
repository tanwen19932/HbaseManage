package hbaseManage.status;

import org.json.JSONObject;
import tw.utils.HttpUtil;

import java.net.MalformedURLException;
public class HbaseMetricsImpl implements HbaseMetricsI{
	String jsonStr = null;
	static final String ERROR = "{\"beans\" : [ ]}";
		
	public HbaseMetricsImpl() {
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


}
