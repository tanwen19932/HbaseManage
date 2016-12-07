package tw.spark.learning._10Trait

/**
  * @author TW
  * @date TW on 2016/12/5.
  */
class OPClass{

}
class OPClassChild extends OPClass{

}
trait OP extends OPClass{
  def add( x:Int, y:Int):Int
}
trait OP2 extends OP{
  def div(x:Int,y:Int):Double={
    x/y
  }
}

class add extends OPClassChild with OP with OP2{
  override def add(x: Int, y: Int): Int = {
    x+y
  }

  override def div(x: Int, y: Int): Double = {
    x/y.asInstanceOf[Double]
  }
}
object Main{
  def main(args: Array[String]): Unit = {
    val op = new add
    val sum = op.add(1,3)
    println(sum)
    println(op.div(1,3))
  }
}
