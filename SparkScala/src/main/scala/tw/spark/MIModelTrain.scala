package tw.spark

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
	* @author TW
	* @date TW on 2016/12/9.
	*/
class MIModelTrain {

}

object MIModelTrain {
	def main(args: Array[String]): Unit = {
		val jarPath = List("/Users/TW/jars/MIModel.jar")
		try {
			Hdfs.rmr("/words")
		} catch {
			case _ =>
		}
		val conf = new SparkConf()
			.setJars(jarPath)
			.setAppName("MIModel")
			.set("spark.executor.memory", "1g")
			.setMaster("spark://tanwendeMacBook-Pro.local:7077")
		val sc = new SparkContext(conf)
		val regex = """([^\s]+)\s+([0-9]+)""".r
		val allText = sc.textFile("/all").persist(StorageLevel.MEMORY_AND_DISK_SER)
		val wordCntList = allText.map(line => {
			line.split("\t").map(p => {
				val regex(name, count) = p
				(name, Integer.valueOf(count))
			})
		})

		val words = wordCntList.flatMap(pairs => pairs.map(a => a._1)).distinct()
		words.repartition(1).saveAsTextFile("/words")

		println(words.count())
	}

}