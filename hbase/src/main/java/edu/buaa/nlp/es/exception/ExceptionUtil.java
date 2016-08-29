/*    */ package edu.buaa.nlp.es.exception;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ public class ExceptionUtil
/*    */ {
/*    */   public static String getExceptionTrace(Throwable e)
/*    */   {
/*  9 */     if (e != null) {
/* 10 */       StringWriter sw = new StringWriter();
/* 11 */       PrintWriter pw = new PrintWriter(sw);
/* 12 */       e.printStackTrace(pw);
/* 13 */       return sw.toString();
/*    */     }
/* 15 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\PROJECT\J2eeProject\dataProcessor_weibo_20160701\lib\elasticsearch\ir_zhongyi-6.2.0.jar
 * Qualified Name:     edu.buaa.nlp.es.exception.ExceptionUtil
 * JD-Core Version:    0.6.2
 */