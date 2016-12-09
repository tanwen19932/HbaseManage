package tw.spark.learning

import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting

/**
  * @author TW
  * @date TW on 2016/11/3.
  */
object _3Array {
  def main(args: Array[String]): Unit = {
    val a = new Array[String](10)
    val b = Array("a", "c", "f", "u", "n")
    var c = ArrayBuffer[String]()
    println(c ++= b)
    println(c += "1")
    println(c.trimStart(1))
    println(c.insert(2, "hello"))
    for (i <- c) print(i)
    b(1) = "b"
    println(b)
    var temp = Array.ofDim[Double](3, 4)
    //    var temp2 ： Array[new Array[Int]](3)
    val myArray = Array(1, 3, 4, 211, 5, 24, 5, 1, 4, 65214, 6, 1, 44, 11)
    println(myArray.sum)
    Sorting.quickSort(myArray)
    myArray.foreach(i => print(i + " "))
    println()
    myArray.filter(_ % 2 == 0).map(_ * 2).foreach(x => print(x + " "))
    println(myArray.mkString("<",".",">"))
    //    println(myArray.toStream.map(_ = 2).sum)

  }
}
