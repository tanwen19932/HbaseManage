/*    */ package edu.buaa.nlp.es.sort;
/*    */ 
/*    */ import java.text.Collator;
/*    */ import java.util.Locale;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ChineseSorter
/*    */   implements CustomSorter
/*    */ {
/*    */   private String field;
/*    */   private String order;
/*    */ 
/*    */   public ChineseSorter(String field, String order)
/*    */   {
/* 19 */     this.order = order;
/* 20 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public int compare(JSONObject o1, JSONObject o2)
/*    */   {
/* 25 */     Collator collator = Collator.getInstance(Locale.CHINA);
/* 26 */     if ((this.order == null) || ("".equals(this.order))) this.order = "asc";
/* 27 */     if ("asc".equals(this.order))
/* 28 */       return collator.compare(o1.getString(this.field), o2.getString(this.field));
/* 29 */     if ("desc".equals(this.order)) {
/* 30 */       return collator.compare(o2.getString(this.field), o1.getString(this.field));
/*    */     }
/* 32 */     return 1;
/*    */   }
/*    */ }

/* Location:           D:\PROJECT\J2eeProject\dataProcessor_weibo_20160701\lib\elasticsearch\ir_zhongyi-6.2.0.jar
 * Qualified Name:     edu.buaa.nlp.es.sort.ChineseSorter
 * JD-Core Version:    0.6.2
 */