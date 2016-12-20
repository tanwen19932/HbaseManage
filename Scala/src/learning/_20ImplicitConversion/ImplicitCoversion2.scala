package learning._20ImplicitConversion

import tw.spark.learning._11Operator.Fraction

/**
	* @author TW
	* @date TW on 2016/12/9.
	*/
//主要为 柯里化 作为隐式参数
class ImplicitCoversion2 {
	def smaller[T](a:T,b:T)(implicit order:T=> Ordered[T]): T ={
		if ( order(a) >  b) b else a
	}
}
//上下文界定： 柯里化函数
class pair[T:Ordering](val a:T ,val b:T){
	def smaller(implicit ord:Ordering[T])={
		if(ord.compare(a,b)>0) b else a
	}
}
object test{
	def main(args: Array[String]): Unit = {
		val a = Fraction(1,3)
		val b = Fraction(1,4)
		implicit val fractionOrder:Ordering[Fraction] = new Ordering[Fraction]{
			override def compare(x: Fraction, y: Fraction): Int = {
				val Fraction(x_a,x_b) = x
				val Fraction(y_a,y_b) = y
				if((x_a/x_b)>(y_a/y_b)) 1 else  0
			}
		}
		val ab = new pair[Fraction](a,b)
		println(ab.smaller.toString)
	}
}