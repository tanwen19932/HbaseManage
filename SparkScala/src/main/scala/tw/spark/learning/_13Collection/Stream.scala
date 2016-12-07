package tw.spark.learning._13Collection

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
object StreamExample {
  val ss:Int =0
  def pow(i: Int, i1: Int) = i * i + i1

  def main(args: Array[String]): Unit = {
    def numsFrom(x: Int): Stream[BigDecimal] = x #:: numsFrom(x + 1)

    val tenOrMore = numsFrom(10); //Stream(10 ,?)
    println(
      tenOrMore.tail.tail.tail.map(x => x * x).take(5).force
    )
    //一个map
    val doubleThen1 = (0 to 1000).view.map(pow(10, _)).map(_ * 2)
    //两个map
    val doubleThen2 = (0 to 1000).map(pow(10, _)).map(_ * 2)
    println(doubleThen1.force)
    println(doubleThen2)
    for (i <-(0 until 10).par) {
      print(i + " ")
    }
    var sum:Int = 0
    for (i <-(0 until 10).par) sum+=i
    println(sum)//sum 有问题
  }
}
