/*     */ package es.client;
import edu.buaa.nlp.es.exception.ExceptionUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class SearchBuilder
/*     */ {
/*  27 */   private Client client = null;
/*  28 */   private Logger logger = Logger.getLogger(getClass());
/*  29 */   private IndexBuilder builder = null;
/*     */ 
/*  31 */   private SearchResponse scrollResp = null;
/*  32 */   private int indexSize = 10000;
/*  33 */   private long scrollTime = 60000L;
/*     */ 
			
/*     */   public SearchBuilder() {
/*  36 */     this.client = ESClient.getClient();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try {
/*  42 */       if (this.builder != null)
/*     */       {
/*  44 */         this.builder.close();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  49 */       this.logger.error(ExceptionUtil.getExceptionTrace(e));
/*     */     }
/*  51 */     if (this.client != null)
/*  52 */       this.client.close();
/*     */   }
/*     */ 
/*     */   public boolean initGetAllIndex(String indexName, String indexType, int indexSize, long scrollTime)
/*     */   {
/*     */     try
/*     */     {
/*  65 */       this.indexSize = indexSize;
/*  66 */       this.scrollTime = scrollTime;
/*     */ 
/*  69 */       if (this.client == null)
/*     */       {
/*  71 */         this.client = ESClient.getClient();
/*     */       }
/*  73 */       this.scrollResp = 
/*  78 */         ((SearchResponse)this.client.prepareSearch(new String[] { indexName })
/*  74 */         .setTypes(new String[] { 
/*  74 */         indexType })
/*  75 */         .setSearchType(SearchType.SCAN)
/*  76 */         .setScroll(new TimeValue(scrollTime))
/*  77 */         .setSize(indexSize)
/*  78 */         .execute().actionGet());
/*  79 */       for (SearchHit localSearchHit : this.scrollResp.getHits().getHits());
/*  82 */       return true;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  86 */       e.printStackTrace();
/*  87 */     }return false;
/*     */   }
/*     */ 
/*     */   public List<String> nextGetAllIndex()
/*     */   {
/*     */     try
/*     */     {
/*  95 */       List ids = new ArrayList();
/*  96 */       if (this.scrollResp == null)
/*     */       {
/*  98 */         return null;
/*     */       }
/* 100 */       this.scrollResp = 
/* 101 */         ((SearchResponse)this.client.prepareSearchScroll(this.scrollResp.getScrollId())
/* 101 */         .setScroll(new TimeValue(this.scrollTime)).execute().actionGet());
/* 102 */       if (this.scrollResp.getHits().getHits().length == 0) {
/* 103 */         return null;
/*     */       }
/* 105 */       for (SearchHit searchHit : this.scrollResp.getHits().getHits())
/*     */       {
/* 110 */         ids.add(searchHit.getSourceAsString());
/*     */       }
/* 112 */       return ids;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 116 */       e.printStackTrace();
/* 117 */     }return null;
/*     */   }
/*     */ 
/*     */   public boolean updateUnit(JSONObject jsonObject, String indexName, String indexType, String idName)
/*     */   {
/*     */     try
/*     */     {
/* 126 */       if (this.builder == null)
/*     */       {
/* 128 */         this.builder = new IndexBuilder();
/*     */       }
/* 130 */       return this.builder.updateUnit(jsonObject.toString(), indexName, indexType, idName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 135 */       this.logger.error(ExceptionUtil.getExceptionTrace(e));
/* 136 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean insertUnit(JSONObject jsonObject, String indexName, String indexType, String idName)
/*     */   {
/*     */     try
/*     */     {
/* 144 */       if (this.builder == null)
/*     */       {
/* 146 */         this.builder = new IndexBuilder();
/*     */       }
/* 148 */       return this.builder.addUnit(jsonObject.toString(), indexName, indexType, idName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 153 */       this.logger.error(ExceptionUtil.getExceptionTrace(e));
/* 154 */     }return false;
/*     */   }
/*     */ 
/*     */   public boolean deleteUnit(JSONObject jsonObject, String indexName, String indexType, String idName)
/*     */   {
/*     */     try
/*     */     {
/* 163 */       if (this.builder == null)
/*     */       {
/* 165 */         this.builder = new IndexBuilder();
/*     */       }
/* 167 */       return this.builder.deleteUnit(jsonObject.toString(), indexName, indexType, idName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 172 */       this.logger.error(ExceptionUtil.getExceptionTrace(e));
/* 173 */     }return false;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\PROJECT\J2eeProject\dataProcessor_weibo_20160701\lib\elasticsearch\ir_zhongyi-6.2.0.jar
 * Qualified Name:     edu.buaa.nlp.es.client.SearchBuilder
 * JD-Core Version:    0.6.2
 */