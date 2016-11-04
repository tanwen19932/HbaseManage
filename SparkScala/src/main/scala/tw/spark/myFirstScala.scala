package tw.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author TW
  */
object  myFirstScala {
  def main(args: Array[String]): Unit = {
    println("Hello Scala!")
    val conf = new SparkConf().setAppName("Spark Pi").setMaster("spark://192.168.88.10:7077")  .setJars(List("D:\\zhongyijar\\SparkScala.jar"))
    val sc = new SparkContext(conf)

    val slices = if (args.length > 0) args(0).toInt else 2
    val n = 100000 * slices
    val count =  sc.parallelize(1 to n, slices).map{ i =>
      val x = Math.random*2-1
      val y = Math.random*2-1
      if (x*x + y * y < 1) 1 else 0
    }reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / n)
    sc.stop()
  }
}
