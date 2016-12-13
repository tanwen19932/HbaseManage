package es;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ESBulkProcessorFactory {
	static Logger LOG= LoggerFactory.getLogger(ESBulkProcessorFactory.class);
	
    public static	BulkProcessor buildBulkProcessor(Client client){
    	BulkProcessor bulkProcessor = BulkProcessor.builder(
    	        client,
    	        new BulkProcessor.Listener() {
    	            @Override
    	            public void beforeBulk(long executionId,
    	                                   BulkRequest request) { 
    	            	
    	            } 

    	            @Override
    	            public void afterBulk(long executionId,
    	                                  BulkRequest request,
    	                                  BulkResponse response) { 
    	            	boolean isFail = response.hasFailures();
    	            	LOG.info("请求成功 +++ " + request.numberOfActions()+ 
    	            			"\n++++   是否包含错误 " + isFail);
    	            	if(isFail){
    	            		BulkItemResponse[] responses =response.getItems();
    	            		for(BulkItemResponse aResponse : responses){
    	            			if(aResponse.isFailed()){
    	            				LOG.error(" ES 插入错误："+aResponse.getFailureMessage());
    	            			}
    	            		}
    	            	}
    	            } 

    	            @Override
    	            public void afterBulk(long executionId,
    	                                  BulkRequest request,
    	                                  Throwable failure) { 
    	            	LOG.error("请求失败 +++++++   ", failure);
    	            } 
    	        })
    	        .setBulkActions(10000) 
    	        .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB)) 
    	        .setFlushInterval(TimeValue.timeValueSeconds(5)) 
    	        .setConcurrentRequests(10) 
    	        .setBackoffPolicy(
    	            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)) 
    	        .build();
		return bulkProcessor;
    }
	
	
}
