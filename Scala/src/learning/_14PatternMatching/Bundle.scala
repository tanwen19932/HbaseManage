package tw.spark.learning._14PatternMatching

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
object Bundle {
  def main(args: Array[String]): Unit = {
    val discount = Bundle("Chrismas DISCOUNT!!", 10, Book("spark", 10), Book("hadoop", 8), Book("scala", 10))
    println(discount match {
      case Bundle(_, _, Book(name, _)) => "sale alone"
      case Bundle(_, _, book1@Book(_, _), rest@_*) => "第一本书" + book1.name
      case _ => "一堆垃圾"
    })
  }
}

abstract class Item
//样例类，自动生成apply和unapply方法
case class Book(name: String, price: Int) extends Item

case class Bundle(name: String, price: Int, items: Item*) extends Item

