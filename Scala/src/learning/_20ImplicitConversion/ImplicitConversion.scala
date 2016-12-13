package learning._20ImplicitConversion

import java.io.File

import scala.io.Source

/**
	* @author TW
	**/
class ImplicitConversion {


}
import tw.spark.learning._11Operator.Fraction
object ImplicitConversion {
	//申明隐式函数 implicit 开头 名称为 source2Target

	implicit def int2Fraction(a: Int): Fraction = Fraction(1, a)

	implicit def file2RichFile(file: File) = new RichFile(file)

	val Fraction(a,b) = 3 * Fraction(4, 5) //这里的3 会被隐式转化为Fraction 就是1/3
	println(a,b)
	val contents = new File("ttw").read //你希望有 read 函数 直接转化

	def main(args: Array[String]): Unit = {
		//尽量引用局部化 避免不必要的转化
//		import ImplicitConversion.int2Fraction
	}
}

class RichFile(val from: File) {
	def read = Source.fromFile(from).mkString
}