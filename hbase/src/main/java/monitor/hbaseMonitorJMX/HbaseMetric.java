package monitor.hbaseMonitorJMX;

import javax.management.remote.JMXServiceURL;

public class HbaseMetric {
	String jsonStr = null;
	JMXManager jmxManager = null;
	
	
	public HbaseMetric() {
		// TODO Auto-generated constructor stub
		jmxManager = new JMXManager();
	}
	
	String getJson(){
		jmxManager.getAllAttribute();
		return null;
	}
	public static void main(String[] args) {
		HbaseMetric hbaseMetric = new HbaseMetric();
		System.out.println( hbaseMetric.getJson() );
	}
}
