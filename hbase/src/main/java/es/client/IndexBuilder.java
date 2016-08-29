/*     */ package es.client;
/*     */ 
/*     */ import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.util.Iterator;

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
/*     */ public class IndexBuilder
/*     */ {
/*     */   private Client client;
/*  40 */   private Logger logger = Logger.getLogger(getClass());
/*     */ 
/*     */   public IndexBuilder() {
/*  43 */     this.client = ESClient.getClient();
/*     */   }
/*     */ 
/*     */   public boolean addUnit(String jsonUnit, String indexName, String type, String idKey)
/*     */   {
/*  55 */     XContentBuilder jsonBuilder = null;
/*  56 */     JSONObject json = JSONObject.fromObject(jsonUnit);
/*  57 */     IndexResponse response = null;
/*     */     try {
/*  59 */       jsonBuilder = XContentFactory.jsonBuilder().startObject();
/*  60 */       Iterator it = json.keys();
/*  61 */       String key = "";
/*  62 */       while (it.hasNext()) {
/*  63 */         key = (String)it.next();
/*  64 */         jsonBuilder.field(key, json.get(key));
/*     */       }
/*  66 */       jsonBuilder.endObject();
/*     */     } catch (IOException e) {
/*  68 */       e.printStackTrace();
/*     */     } finally {
/*  70 */       response = (IndexResponse)this.client.prepareIndex(indexName, type, json.getString(idKey)).setSource(jsonBuilder).execute().actionGet();
/*     */     }
/*  72 */     return response.isCreated();
/*     */   }
/*     */ 
/*     */   public int addUnitBatch(String jsonUnitArray, String indexName, String type, String idKey)
/*     */   {
/*  84 */     BulkRequestBuilder brb = this.client.prepareBulk();
/*  85 */     JSONArray array = JSONArray.fromObject(jsonUnitArray);
/*  86 */     JSONObject obj = null;
/*  87 */     String key = "";
/*  88 */     BulkResponse response = null;
/*  89 */     int sum = 0;
/*     */     try {
/*  91 */       for (int i = 0; i < array.size(); i++) {
/*  92 */         XContentBuilder jsonBuilder = XContentFactory.jsonBuilder().startObject();
/*  93 */         obj = array.getJSONObject(i);
/*  94 */         Iterator it = obj.keys();
/*  95 */         while (it.hasNext()) {
/*  96 */           key = (String)it.next();
/*  97 */           jsonBuilder.field(key, obj.get(key));
/*     */         }
/*  99 */         jsonBuilder.endObject();
/* 100 */         brb.add(this.client.prepareIndex(indexName, type, obj.getString(idKey)).setSource(jsonBuilder));
/* 101 */         sum++;
/*     */       }
/*     */     } catch (IOException e) {
/* 104 */       e.printStackTrace();
/*     */     } finally {
/* 106 */       response = (BulkResponse)brb.execute().actionGet();
/*     */     }
/* 108 */     if (response.hasFailures()) {
/* 109 */       System.out.println(response.buildFailureMessage());
/* 110 */       this.logger.error(response.buildFailureMessage());
/*     */     }
/* 112 */     return sum;
/*     */   }
/*     */ 
/*     */   public boolean updateUnit(String jsonUnit, String indexName, String type, String idKey)
/*     */   {
/* 122 */     JSONObject json = JSONObject.fromObject(jsonUnit);
/* 123 */     if ((!json.containsKey(idKey)) || (json.getString(idKey) == null)) {
/* 124 */       return false;
/*     */     }
/* 126 */     UpdateRequest ur = new UpdateRequest();
/* 127 */     ((UpdateRequest)ur.index(indexName)).type(type).id(json.getString(idKey));
/*     */     try {
/* 129 */       XContentBuilder xcb = XContentFactory.jsonBuilder().startObject();
/* 130 */       Iterator it = json.keys();
/* 131 */       String key = "";
/* 132 */       while (it.hasNext()) {
/* 133 */         key = (String)it.next();
/* 134 */         if (!key.equals(idKey))
/* 135 */           xcb.field(key, json.get(key));
/*     */       }
/* 137 */       xcb.endObject();
/* 138 */       ur.doc(xcb);
/*     */     } catch (IOException e) {
/* 140 */       e.printStackTrace();
/*     */     } finally {
/* 142 */       this.client.update(ur);
/*     */     }
/* 144 */     return ur.docAsUpsert();
/*     */   }
/*     */ 
/*     */   public boolean deleteUnit(String jsonUnit, String indexName, String indexType, String idKey)
/*     */   {
/* 153 */     JSONObject json = JSONObject.fromObject(jsonUnit);
/* 154 */     if ((!json.containsKey(idKey)) || (json.getString(idKey) == null)) {
/* 155 */       return false;
/*     */     }
/* 157 */     DeleteResponse dr = (DeleteResponse)this.client.prepareDelete(indexName, indexType, json.getString(idKey)).execute().actionGet();
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */   public void deleteByType(String indexName, String type)
/*     */   {
/* 166 */     QueryBuilder qb = QueryBuilders.termQuery("_type", type);
/* 167 */     DeleteByQueryResponse dbr = (DeleteByQueryResponse)this.client.prepareDeleteByQuery(new String[] { indexName }).setQuery(qb).execute().actionGet();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 183 */     if (this.client != null)
/* 184 */       this.client.close();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws InterruptedException
/*     */   {
/* 190 */     IndexBuilder builder = new IndexBuilder();
/* 191 */     JSONObject object = new JSONObject();
/*     */   }
/*     */ }

/* Location:           D:\PROJECT\J2eeProject\dataProcessor_weibo_20160701\lib\elasticsearch\ir_zhongyi-6.2.0.jar
 * Qualified Name:     edu.buaa.nlp.es.client.IndexBuilder
 * JD-Core Version:    0.6.2
 */