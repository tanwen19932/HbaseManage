package tw.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author TW
  * @date TW on 2016/12/6.
  */
object SimpleApp {
  def main(args: Array[String]): Unit = {
    val logFile = "/README.md"
    val conf = new SparkConf()
      .setMaster("spark://tanwendeMacBook-Pro.local:7077")
      .setAppName("???")
    val sc = new SparkContext(conf)
    val n, te = 1
    val count = sc.parallelize(1 to n, te).map { i =>
      val x = Math.random * 2 - 1
      val y = Math.random * 2 - 1
      if (x * x + y * y < 1) 1 else 0
    } reduce (_ + _)
    println("Pi is roughly " + 4.0 * count / n)
    sc.stop()
  }
}
