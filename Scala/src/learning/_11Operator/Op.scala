package tw.spark.learning._11Operator

/**
  * @author TW
  * @date TW on 2016/12/7.
  */
class Op {

}
object Main{
  def main(args: Array[String]): Unit = {
    val duplicate = (x:Double)=>2*x
    println(duplicate(3))
  }
}


class Fraction(n:Int,d:Int)
{
  private val num =n
  private val den=d
  def *(other:Fraction) = new Fraction(num*other.num,den*other.den)
  //重载称号操作符
}


object Fraction
{
  def apply(n:Int,d:Int)  = new Fraction(n,d)
  def unapply(input:Fraction)=
  {
    if(input.den==0) None
    else Some((input.num,input.den))
  }
}





object Example11 extends App{
  //元组提取器
  val Fraction(a,b) = Fraction(1,2)*Fraction(1,3)
  println(a,b)



  //额外, 单个参数或者无参数的需要用option;提取任意长度的需要用unapplaySeq



}