package es.test;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ESIndex {
	public static void main(String[] args) throws ParseException, UnknownHostException {
		//		Client client = ESClient.getClient("ctoes", "localhost", 9200);
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", "es").build();
		Client client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.59.10"), 9300));
		JSONObject requestJson = new JSONObject();
		JSONObject setJson = new JSONObject();
		setJson.put("number_of_shards", 20);
		setJson.put("number_of_replicas", 0);
		requestJson.put("settings", setJson);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		calendar.setTime(dateFormat.parse("201501"));
		List<String> indexs = new LinkedList<>();
		while (true) {
			//			System.out.println(dateFormat.format(calendar.getTime()));
			indexs.add("news"+ dateFormat.format(calendar.getTime()) );
			calendar.add(Calendar.MONTH, 1);
			if (dateFormat.format(calendar.getTime()).startsWith("2017")) {
				break;
			}
		}
		for(String index : indexs){
			CreateIndexResponse response = client.admin().indices().prepareCreate(index)
					.setSettings(Settings.builder()
							.put("index.number_of_shards", 3)
							.put("index.number_of_replicas", 1)
					)
					.get();

			System.out.println(response.getContext());
			//			 System.err.println(" curl -XPOST 'http://192.168.55.194:9200/"+index+"?pretty' -d '{\"settings\":{\"number_of_shards\":20,\"number_of_replicas\":1}}'");
			//			 System.err.println(" curl -XPOST 'http://localhost:9200/"+index+"?pretty' -d '{\"settings\":{\"number_of_shards\":20,\"number_of_replicas\":0}}'");

		}

		// LocalDateTime coffeeBreak = LocalDateTime.of(2015,01, 01, 00, 00);
		// int month = 1;
		// while(true){
		// coffeeBreak.plusMonths(month);
		// month++;
		// System.out.println(coffeeBreak.getYear() + ""
		// +coffeeBreak.getMonth().toString());
		// if (coffeeBreak.getYear()>2016){
		// break;
		// }
		// }
	}
}
