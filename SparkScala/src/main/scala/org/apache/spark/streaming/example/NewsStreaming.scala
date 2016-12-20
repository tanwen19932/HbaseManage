package org.apache.spark.streaming.example

import java.io.File

import edu.buaa.nlp.entity.news.{DicMap, ProcessedNews}
import handler.news.NewsHandlerChainV2
import jwtech.spark.streaming.receiver.news.HbaseReceiver
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.json.JSONObject

import scala.collection.mutable.ArrayBuffer


/**
	* @author TW
	* @date TW on 2016/12/14.
	*/

object NewsStreaming {
	def main(args: Array[String]): Unit = {
		val jarsPath = "/Users/TW/jars/"
		val handler = new NewsHandlerChainV2
		val news = new ProcessedNews
		val libpath = new File( jarsPath + "NewsStreaming/lib/")
		var jars = new ArrayBuffer[String]()
		jars ++= (libpath.list().map(libpath.getAbsolutePath+"/"+_))
		jars += (jarsPath+"NewsStreaming/NewsStreaming.jar")
		println(jars)
		news.setMediaTname("新浪新闻")
		news.setUrl("  aa ")
		news.setTitleSrc("aaa")
		news.setTextSrc("\"/Users/TW/Library/spark-2.0.2-bin-hadoop2.7/work/NewsStreaming\"\"/Users/TW/Library/spark-2.0.2-bin-hadoop2.7/work/NewsStreaming\"\"/Users/TW/Library/spark-2.0.2-bin-hadoop2.7/work/NewsStreaming\"")
		news.setPubdate("2016-09-20 19:00:00")
		news.setMediaNameSrc("Sina")
		news.setLanguageCode("en")
		news.setComeFrom("TW")
		news.setCountryNameZh("美国")
		news.setMediaLevel(1)
		handler.handle(news)
		println(news)
		System.setProperty("user.dir","/")
		val sparkConf = new SparkConf()
			.setJars(jars.toList)
			.setAppName("NewsStreaming")
			.setMaster("spark://localhost:7077")
		  .set("--num-executors","1")
		val ssc = new StreamingContext(sparkConf, Seconds(10))
		val newsStream = ssc.receiverStream(new HbaseReceiver())
		ssc.checkpoint(".")

		newsStream.foreachRDD(rdd => {
			rdd.foreach(str=>{
				val json = new JSONObject(str)
				val news = new ProcessedNews
				news.setMediaTname("新浪新闻")
				news.setUrl("  aa ")
				news.setTitleSrc(json.getString("title"))
				news.setTextSrc(json.getString("text"))
				news.setPubdate(json.getString("pubDate"))
				news.setMediaNameSrc("Sina")
				news.setLanguageCode("en")
				news.setComeFrom("TW")
				news.setCountryNameZh("美国")
				news.setMediaLevel(1)
				println("before :"+news)
				val file = new File("TW");
				println("Excutor 能用文件 ？" + file.getAbsolutePath)

				//		val handler = new NewsHandlerChainV2   //不能序列化
				handler.handle(news)
				news.setLanguageTname(DicMap.getLanguageZh(news.getLanguageCode))
//				val handler = new NewsHandlerChainV2
//				handler.handle(news)
				println("after :"+news)
			})
		})
//		news.foreachRDD(rdd => {
//			rdd.foreachPartition(part => {
////				val handler = new NewsHandlerChainV2
//				part.foreach(str => {
//					val json = new JSONObject(str)
//					val news = new ProcessedNews
//					news.setMediaTname("新浪新闻")
//					news.setUrl("  aa ")
//					news.setTitleSrc(json.getString("title"))
//					news.setTextSrc(json.getString("text"))
//					news.setPubdate(json.getString("pubDate"))
//					news.setMediaNameSrc("Sina")
//					news.setLanguageCode("en")
//					news.setComeFrom("TW")
//					news.setCountryNameZh("美国")
//					news.setMediaLevel(1)
//					println(news)
////					handler.handle(news)
//					println(news)
//				})
//			})
//		})
		newsStream.print()
		ssc.start()
		ssc.awaitTermination()
	}
}
