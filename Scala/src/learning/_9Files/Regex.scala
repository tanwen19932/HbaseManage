package tw.spark.learning._9Files

import scala.io.Source

/**
	* @author TW
	* @date TW on 2016/12/8.
	*/
object Example9 extends App {

//	val source = Source.fromFile("/home/linger/data/hive_data","UTF-8")
//
//	val lines = source.getLines()
//	for(l<-lines)
//	{
//		println(l)
//	}
//
//	lines.toArray
//	source.mkString
//	source.close()

	//可以调用Java一些io库来处理文件


	//正则表达式
	val wsnumws = """\s[0-9]+\s+""".r   //原始字符串形式
	val wsnumws2 = "\\s[0-9]+\\s+".r //等价于上面,但需要转移


	//正则提取器
	val numitem = "([0-9]+) ([a-z]+)".r
	val numitem(num,item) = "99 bottle"

	val regex = """([^\s]+)\s+([0-9]+)""".r
	val regex(name ,count) = "掉了 55"
	println(name,count)
	println(num,item)




}
