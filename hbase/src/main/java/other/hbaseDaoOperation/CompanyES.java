package other.hbaseDaoOperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.json.JSONException;
import org.json.JSONObject;

import es.client.ESClient;
import hbase.util.Murmurs;
import tw.utils.JsonUtil;

public class CompanyES {
	public static void main(String[] args) throws JSONException, ElasticsearchException, IOException {
		Client client = ESClient.getClient("yeesightNew","192.168.55.45",9300);
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		BufferedReader bf = new BufferedReader( new FileReader("E:\\insert\\公司名称.txt"));
		String line ;
		int count = 0;
		int total = 0;
		BulkResponse bulkResponse;
		while( ( line = bf.readLine() ) !=null ){
//			line = line.replace("^/d*", "");
			long time = System.currentTimeMillis();
			String id = Murmurs.hashStr(line);
			JSONObject json = new JSONObject();
			json.put("companyName", line);
			json.put("id", id);
			
			bulkRequest.add(client.prepareIndex("product", "company", id).setSource(json.toString()));
			count++;
//			System.out.println( (System.currentTimeMillis()-time)/1000 );
			if (count == 100) {
				bulkResponse = bulkRequest.execute().actionGet();
				bulkRequest = null;
				bulkRequest = client.prepareBulk(); 
				System.out.println( total );
				total +=count;
				count = 0;
			}
		}
		bulkResponse = bulkRequest.execute().actionGet();
		
		
		
	}
}
