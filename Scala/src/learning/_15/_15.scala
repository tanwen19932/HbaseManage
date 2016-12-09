package tw.spark.learning._15

/**
	* @author TW
	* @date TW on 2016/12/9.
	*/
class _15 {
	//注解可以在程序中的各项条目添加信息，这些信息可以被编译器或外部工具处理。

	//下面是一个使用注解，指导编译器编译，为了供java方便调用。
	import scala.annotation.varargs
	@varargs
	def process(args:String*)
	{}
	//则编译器生成java方法：void process(String... args) ，然后java就可以很方便调用这个scala方法
}
