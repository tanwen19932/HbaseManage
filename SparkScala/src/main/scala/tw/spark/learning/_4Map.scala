package tw.spark.learning

import scala.collection.immutable.TreeMap

/**
  * @author TW
  * @date TW on 2016/11/4.
  */
object _4Map {
  def main(args: Array[String]): Unit = {
    var map = Map("a" -> 100, "b" -> 200)
    map("a")
    println(map.get("a") + " =" + map("a"))
    println(map.getOrElse("c", 105))

    val pair1 = (1, "a")
    println(pair1._2)
    val symbol = Array("b", "t", "c")
    val count = Array(2, 3, 2)
    def myFuc =  (a:String,b:Int)=> (b>=2)
    val symMap = symbol.zip(count).toMap.filter( (a)=>a._2>2 ).map((a)=>(a._2,1))
    val mapping = TreeMap("1"->"Mondy","2"->"te")
    for ( (a,b) <- symMap  ) print(a*b)

  }
}
