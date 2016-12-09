package tw.spark.learning._8Extends

/**
  * @author TW
  **/
class _8Extends {

}
abstract class man {
  val age: Int;
}
class bigMan extends man{
  override val age: Int =1
}
abstract class creature{
  val range = 10;
  val env = Array[Int]{range}
}
class ant extends {
  override val range =2;
}with creature