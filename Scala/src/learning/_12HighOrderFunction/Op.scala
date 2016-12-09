package tw.spark.learning._12HighOrderFunction

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
class Op {

}

object Main {
  def main(args: Array[String]): Unit = {
      def mulBy(factor: Double) = (x: Double) => factor * x

      def double(x: Int)(y: Int) = x * y
      //科里化（currying）
      def util(condition: => Boolean)(Block: => Unit) {
        if (!condition) {
          println(condition)
          Block
          util(condition)(Block)
        }
      }

      var x = 10
      util(x == 0) {
        x -= 1
        if (x == -1) {
          Thread.sleep(5000)
        }
        println(x)
      }
      //高阶函数
      Array(3.4, 44.5, 2222.4).map(mulBy(5)) foreach (println(_))
  }
}

