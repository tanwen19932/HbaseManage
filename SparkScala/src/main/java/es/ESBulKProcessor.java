package es;


import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * Created by bombJ on 2016/9/25.
 *
 * 用与缓存es请求并在满足条件时触发批量处理
 */
public class ESBulKProcessor {
    private BulkProcessor bulkProcessor = null;

    public ESBulKProcessor(TransportClient client, BulkHandler handler){
        this(client, 600, new ByteSizeValue(100, ByteSizeUnit.MB), new TimeValue(60, TimeUnit.SECONDS), 3,                  handler);
    }

    public ESBulKProcessor(TransportClient client, int actoins, ByteSizeValue size, TimeValue time, int                 concurrents, final BulkHandler handler){
        bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener(){
                    public void beforeBulk(long executionId, BulkRequest request) {
                        System.out.println("[start a new bulk]");
                    }

                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        System.out.println("[processed a bulk]");
                    }

                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        System.out.println("[error occurred]↓");
                        failure.printStackTrace();
                        if (handler != null){
                            handler.handleFailure(request);
                        }
                    }
                })
        .setBulkActions(actoins)
        .setBulkSize(size)
        .setFlushInterval(time)
        .setConcurrentRequests(concurrents)
        .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(1000), 3))
        .build();
    }

    public void processRequest(ActionRequest request){
        this.bulkProcessor.add(request);
    }

    public void shutdown() throws Exception{
        //默认等待10分钟后强制关闭
        this.shutdown(new TimeValue(120, TimeUnit.SECONDS));
    }

    public void shutdown (TimeValue waitTime) throws Exception{
        this.bulkProcessor.awaitClose(waitTime.getMillis(), TimeUnit.MILLISECONDS);
    }

    public void shutdownNow(){
        this.bulkProcessor.close();
    }
    
    public void flushLastCache(){
        this.bulkProcessor.flush();
    }

    public interface BulkHandler{
        public void handleFailure(BulkRequest request);
    }
}
