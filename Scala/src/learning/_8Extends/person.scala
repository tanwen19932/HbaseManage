package tw.spark.learning._8Extends

/**
	* @author TW
	* @date TW on 2016/12/8.
	*/
package tw.spark.learning._8Extends


class Person8
{
	var name:String=""
	def this(name:String)
	{
		this()//一定要先调用主构造器.每个辅助构造器最终都会调用主构造器
		this.name=name
	}
	override def toString=
	{
		"name:"+name
	}
	def show=
	{
		println("Person:"+toString)
	}
}

class Employee(name:String,salary:Double) extends Person8(name:String) //只有主构造器能够调用超类的构造器
{
	override def toString=
	{
		"name:"+name+",salary:"+salary
	}

	override def show=
	{
		println("Employee:"+toString)
	}


}

object Example8 extends App{
	val t1 = new Person8("linger")
	println(t1)
	t1.show

	val t2 = new Employee("linger",15)
	println(t2)
	t2.show

	val t3:Person8 = t2   //这里跟c++不一样阿,c++必须是虚函数,才会呈现多态.scala里没有虚函数,所有函数只要被override了都会呈现多态
	println(t3)
	t3.show
}
