package es;


import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.concurrent.TimeUnit;

/**
 * Created by bomb.J on 2016/9/25.
 * 用于大批量、全量查询，旨在防止机器爆炸、内存溢出
 */


public class ESScrollSearchProcessor {
    TimeValue timeAlive  = null;
    int indicesPerShade = 0;
    TransportClient client = null;
    SearchRequestBuilder searchRequestBuilder = null;

    public ESScrollSearchProcessor(TransportClient client, String[] indices, QueryBuilder queryBuilder){
        this(client, indices, queryBuilder, new TimeValue(90000,TimeUnit.MILLISECONDS), 100);
    }

    public ESScrollSearchProcessor(TransportClient client, String[] indices , QueryBuilder queryBuilder, TimeValue timeAlive, int indicesPerShade){
        searchRequestBuilder = client.prepareSearch(indices)
                .setQuery(queryBuilder)
                .setScroll(timeAlive)
                .setFetchSource(false)//不返回_source,节省IO资源
                .setSize(indicesPerShade);
        this.timeAlive = timeAlive;
        this.indicesPerShade = indicesPerShade;
        this.client = client;
    }

    public void doScroll(ScrollSearchHandler handler){
        SearchResponse scrollResponse = searchRequestBuilder.execute().actionGet();
        while (true) {
            SearchHit[] hits = scrollResponse.getHits().getHits();
            if (hits.length == 0) {
               //System.out.println("[ESScrollSearchProcessor.doScroll()]: no documents to scroll out");
                return;
            }
            //handle a bulk of query-results:
            handler.handleHits(hits);
            scrollResponse = client.prepareSearchScroll(scrollResponse.getScrollId())
                    .setScroll(timeAlive)
                    .execute()
                    .actionGet();
        }
    }

    public interface ScrollSearchHandler{
        public void handleHits(SearchHit[] hits);
    }
}
