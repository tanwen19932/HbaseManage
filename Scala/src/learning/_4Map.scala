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

object Example4 {

  def main(args: Array[String])
  {
    //不可变Map
    val scores= scala.collection.immutable.Map("linger"->1,"angela"->2)
    //scores+=("lanlan"->3)会报错

    //可变Map
    val scores2 = scala.collection.mutable.Map("linger"->9388296,"angela"->2)
    scores2+=("lanlan"->3) //增
    scores2-=("lanlan") //删
    scores2("linger") = 4 //改
    scores2("linger")  //查

    for((k,v)<- scores)  yield(v,k) //遍历,实现反转

    scores2.isDefinedAt("key")
    scores2.contains("key")

    val scores3 = new scala.collection.mutable.HashMap[String,Int]()
    val scores4 = new  scala.collection.mutable.LinkedHashMap[String,Int]()
    //还有各种map......


    //元组
    val t = ("linger","09388296","24")
    val name=t._1
    val num=t._2
    val age=t._3
    val (name2,num2,age2)=t
    println(name2,num2,age2)

    //zip操作
    val keys=Array("name","age")
    val values=Array("linger",24)
    val pairs = keys.zip(values)
    for((k,v)<-pairs) println(k,v)

    val map = pairs.toMap
    println(map)
  }



}