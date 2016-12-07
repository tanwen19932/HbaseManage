package tw.spark.learning._14PatternMatching
import scala.collection.JavaConversions._

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
object Match {
  def main(args: Array[String]): Unit = {
    val sign = "abcdefsss"
    val c: Char = '1'
    val s = 's'
    var t = c match {
      case '+' => "+"
      case '-' => "-"
      case ' ' => "x"
      case s => "SOS！"
      //守卫
      case _ if Character.isDigit(c) => "no!"
      case _ => sign
    }

    println(t)
    val obj = "5131223"
    val int = obj match {
      //      case x :Int => x
      case y: String => Integer.valueOf(y)
      //      case _ :BigInt => Int.MaxValue
      case _ => 0
    }
    println(int.asInstanceOf[Int] + 1111)
    var arry = Array(0)
    println(arry match {
      case Array(0) => "0"           //0::Nil
      case Array(x, y) => x + " " + y// x::y::Nil
      case Array(0, _*) => " 0 ...."// 0::tail
      case _ => "Else"
    })

    val (x, y) = (1, "Boom")
    val (q , r) = BigInt(10) /% 3
    println(x,y,q,r)
    //守卫
    for ( (k,v) <- System.getProperties() if v =="") {
      println(k+ "==>"  +v)
    }
  }

}
