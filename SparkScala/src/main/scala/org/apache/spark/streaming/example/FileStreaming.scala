package org.apache.spark.streaming.example

import org.apache.hadoop.mapred.{InputFormat, TextInputFormat}
import org.apache.hadoop.mapred.MapOutputCollector.Context
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._

/**
	* @author TW
	* @date TW on 2016/12/3.
	*/
object FileStreaming {
	def main(args: Array[String]): Unit = {
		println("hello word " * 3)
		val jarsPath = "/Users/TW/jars/"
		val sparkConf = new SparkConf()
			.setJars(List(jarsPath + "FileStreaming.jar"))
			.setAppName("FileStreaming")
			.setMaster("spark://localhost:7077")
		val ssc = new StreamingContext(sparkConf, Seconds(10))
		val lines = ssc.textFileStream("/news/")
		ssc.checkpoint(".")
		val words = lines.flatMap(_.split(" "))
		val wordDstream = words.map(x => (x, 1))
		//		val windowedWordCounts = wordDstream.reduceByKeyAndWindow((a: Int, b: Int) => (a + b), Seconds(30), Seconds(10))
		//		windowedWordCounts.print()
		//    普通的wordStream
		val wordCounts = wordDstream.reduceByKey(_ + _)
		wordCounts.print()
		if (wordCounts.count() != 0L) {
			println(" the word count : " + String.valueOf(wordCounts.count())* 4)
			wordCounts.saveAsTextFiles("TW", "fileStreaming")
		}
		ssc.start()
		ssc.awaitTermination()
	}
}
