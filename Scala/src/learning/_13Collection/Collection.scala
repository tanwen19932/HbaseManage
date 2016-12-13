package tw.spark.learning._13Collection

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
object Collection {
  def main(args: Array[String]): Unit = {
    val digits = List(1,34,5,6,88,2)
//    List(9, 1, 34, 5, 6, 88, 2)
    val app1 = List(9):::digits

    //    List(9, 1, 34, 5, 6, 88, 2)
    val app2 = List(9)++:digits

    //    List(9, 1, 34, 5, 6, 88, 2)
    val app3 = List(9)++digits

    //    List(9, List(1, 34, 5, 6, 88, 2))
    val app4 = List(9):+digits

    //    List(List(9), 1, 34, 5, 6, 88, 2)
    val app5 = List(9)+:digits

    //    List(List(9), 1, 34, 5, 6, 88, 2)
    val app6 = List(9)::digits
    136
    136
    136
    println(app1)
    println(app2)
    println(app3)
    println(app4)
    println(app5)
    println(app6)
    //折叠
    println(digits.reduceRight(_+_))
    println(digits.foldLeft(0)(_+_))
    println( (0 /: digits)(_+_))

  }
}
