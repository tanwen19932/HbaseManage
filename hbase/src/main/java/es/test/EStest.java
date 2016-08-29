package es.test;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EStest {
	public static void main(String[] args) {
//		Node node = NodeBuilder().node();
//		Client client = node.client();
//		// on shutdown
//		
		Settings settings =  ImmutableSettings.settingsBuilder().put("cluster.name", "yeesight").build(); 
		
		Client client =  new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.55.13", 9300));
		
//		Client client = ESClient.getClient();
		
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";
		GetResponse response = client.prepareGet("product", "product2", "1")
		        .execute()
		        .actionGet();
		
		// Index name
		System.out.println(response.getSourceAsString());
		// Type name
//		String _type = response.getHeader(key);
		// Document ID (generated or not)
//		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
//		long _version = response.getVersion();
//		System.out.println(_index+ _type+ _id);
		
	}
}
