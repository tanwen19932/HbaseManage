package tw.spark.learning

import java.net.URL

import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException

import scala.io.StdIn

/**
  * @author TW
  * @date TW on 2016/11/3.
  */
object _2Control {

  def main(args: Array[String]): Unit = {
    //条件
    {
      //if many type
      println(if (0 > 1) 1 else "Wrong")
      //if void type
      println(if (0 > 1) 1)
      //if  unseen different line
      if (1 > 2) "cant go to the line "
      else if (1 < 2) "can't move heere" +
        "can add here"
      //多条语句
      if (1 < 2) {
        println("yes");
        println("to");
        println({
          val a = 1
          val b = 2
          a + b
          var c = 1
        })
      }
      val name = StdIn.readLine("\"输入你的大名\"")
      println("\"输入你的age\"")
      val age = StdIn.readInt()
      printf("%s 你是个 %d 岁的 ", name, age)
    }
    //循环
    {
      while (true) {
        println({})
        Thread.sleep(100)
      }
      //      for
      def abs(x: Int) = if (x >= 0) x else -x
      def abs2(s:String): Unit ={
        println(s)
      }
      for (i <- 10 to 0; from = i + 1; j <- i - 6 to from)
        yield abs(i + j)
      System.out.print("a")
      try{
         new URL("111111")
      }catch {
        case _:MalformedURIException => println("")
      }
    }

  }

}
