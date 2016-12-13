import java.util.{HashMap => utilHashMap}
//引入重命名
//import java.util.{HashMap => JavaHashMap}

//隐藏,下面这样就访问不到java的HashMap了
//import java.util.{HashMap => _}

//引入重命名和隐藏都可以解决 不同包的类名相同导致的冲突(或者覆盖)
//目前还不知道啥时候会冲突,啥时候会覆盖.总之要记住,如果使用的类存在名字相同,就要注意了,简单有效d的处理方法是 重命名

//import java.lang._
//import scala._
//import Predef._

/**
  * @author TW
  **/
package edu {
  package buaa {
    package nlp {

      class _7Package {
        def pckage(): Unit = {
          printf("aaaa!")
        }
      }

    }

  }

}


class Angela
{
  //包可见性 这里规定了这个方法只能再examples包内可见
  //当时在spark的mllib做二次开发时遇到这个坑,mllib有些函数是包可见的,其他包访问不了.
  //解决方法是将自己的程序起名跟那些函数所在的包一致,比如org.apache.spark.mllib.xxxx,这样就可以访问mllib的一些类和函数了
  private def sayHello= {
    println("Hello!")
  }

}




object Example7 extends App{

  new StringBuilder


}

object Main {
  def main(args: Array[String]): Unit = {
    val pak = new edu.buaa.nlp._7Package
    pak.pckage()
    var map = new utilHashMap
    // map.put("","")
    printf(map.get(1))
  }
}
package object learning {

}

