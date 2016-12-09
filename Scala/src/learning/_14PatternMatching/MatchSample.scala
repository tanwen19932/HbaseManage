package tw.spark.learning._14PatternMatching

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
object MatchSample {
  def main(args: Array[String]): Unit = {
    val myPockey: Amount = Dollar(10000);
    val his = Currency(333.33, "$")
    //样例类
    val herPockey = his.copy(value = 1000)

    println(myPockey match {
      case Dollar(x) => "$" + x
      case Currency(x, y) => y + " " + x
      case x Currency y => y + " " + x //等同于上 中置表示法
      case Nothing => "Poor Man！"
      case _ => "??????"
    })
    //偏函数 不对所有输入都有定义的函数
    //所有偏函数都是PartialFunction[A,B]的一个实例，A是输入类型，B是输出类型
    val f: PartialFunction[Char, Int] = {
      case '+' => -1;
      case '-' => 0
    }
    print(f('-'))
    println(f.isDefinedAt(')'))
    //偏函数可以是参数
    val res = "-3+4".collect{
      case '+' => 1
      case '-' => -1
    }
    println(res)
  }
}

//密封类 将超类声明为sealed，则其子类必须在同一个文件（超类所在文件）中定义
//密封样例类 可以模拟枚举类型

sealed abstract class Amount
//样例类，自动生成apply和unapply方法
case class Dollar(value: Double) extends Amount

case class Currency(value: Double, unit: String) extends Amount

case object Nothing extends Amount
