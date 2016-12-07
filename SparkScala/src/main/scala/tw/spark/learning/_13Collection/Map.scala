package tw.spark.learning._13Collection

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
object Mapper {
  def main(args: Array[String]): Unit = {
    val a = (Map[Char, Int]() /: "mississipi"){
      (m , c) =>m+(c->(m.getOrElse(c,0)+1))
    }
    //拉链
    val t = List("1","2","3") zip  List(100,200,300)
    t.foreach(println(_))
//    val a = Map[Char, Int]()
//    for (c <- "mississipi") a(c) = (a.getOrElse(c, 0)+1)
    println(a)
    print(a.par.count(_._2>2))
  }
}
