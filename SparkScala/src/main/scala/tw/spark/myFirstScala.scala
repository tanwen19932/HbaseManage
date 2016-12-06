package tw.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author TW
  */
object  myFirstScala {
  def main(args: Array[String]): Unit = {
    println("Hello Scala!")

    val conf = new SparkConf()
      .setJars(List("/Users/TW/jars/SparkPi.jar"))
      .setAppName("Spark Pi2")
      .setMaster("spark://tanwendeMacBook-Pro.local:7077")
    val sc = new SparkContext(conf)
    val slices = if (args.length > 0) args(0).toInt else 2
    println("???????")
    val n = 100 * slices
    val count =  sc.parallelize(1 to n, slices).map{ i =>
      val x = Math.random*2-1
      val y = Math.random*2-1
      if (x*x + y * y < 1) 1 else 0
    }reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / n)
    sc.stop()
  }
}
