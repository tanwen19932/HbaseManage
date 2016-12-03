package tw.spark.learning

import scala.math._
/**
  * @author TW
  * @example TW on 2016/11/3.
  */
object _1Simplest {

  def main(args: Array[String]): Unit = {
    val iterable = 100
    var sum = 0.0
    val pair = (99, "Luftballons")
//    10max(1)
    def myAdd(x: Int*):Int = {
      var sum = 0
      for (i <- x  )
        sum = sum + i
      sum
    }

   println(myAdd(0 to iterable:_*))
   println( {
     for (i <- 0 to iterable ;from= i+1;j <- i-6 to from )
       yield i+j
   })
    val temp = "temp"
    println(temp.apply(1) + "="+ temp.substring(1,2)*3 +  temp.charAt(0)+temp.charAt(temp.length-1) )
    println(sum + " hellooooo".distinct + "temp".patch(1,"ree",4))
  }
}
