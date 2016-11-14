package tw.spark.learning

import tw.spark.learning.trafficLight.trafficLight

/**
  * @author TW
  * @date TW on 2016/11/7.
  */
object _6Object {
  def main(args: Array[String]): Unit = {
    val myArray2 = myArray(1)
    val a2 = new myArray
    a2.display()
    myArray2.display()
    for(color<-trafficLight.values){
      printf(color.id+""+color)
    }
  }
}
object myArray{
  def apply: myArray = new myArray(0)
  def apply(a: Int ): myArray = new myArray(a)
}
class myArray{
  var a = 0
  class myArray(){
  }
  def this(a:Int){
    this()
    this.a= a
  }
  def display(): Unit ={
    println(trafficLight(a))
  }

}
abstract class add{
  def add():Unit
}
object myAdd extends add{
  override def add(): Unit = ???
}
object trafficLight extends Enumeration{
  type trafficLight=Value
  val RED,GREEN,YELLOW= Value
}