package es.test;

import java.util.Calendar;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.json.JSONObject;

import es.client.ESClient;


public class ESIndex {
	public static void main(String[] args) {
		Client client = ESClient.getClient("ctoes","localhost",9200);
		JSONObject requestJson = new JSONObject();
		JSONObject setJson = new JSONObject();
		setJson.put("number_of_shards", 20);
		setJson.put("number_of_replicas", 0);
		requestJson.put("settings", setJson);
		String index = null;
		IndexRequestBuilder indexRequestBuilder = new IndexRequestBuilder(client);
		indexRequestBuilder.setSource(requestJson).execute();
	}
}	
