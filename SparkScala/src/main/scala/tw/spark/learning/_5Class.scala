package tw.spark.learning

import scala.beans.BeanProperty

/**
  * @author TW
  * @date TW on 2016/11/4.
  */
object _5Object {
  def main(args: Array[String]): Unit = {
    val xiaoming = new Person("xiaoming", 13)
    val xiaohua = new Person("xiaohua")
    val xiaotan = new Person2;
    xiaotan.name = "tan"
    xiaotan.age = 5
    print(xiaohua + "" + xiaoming + xiaotan)
  }
}

class Person {
  private[this] var name = "";
  private var age = 1;

  class Person() {
  }

  def this(name: String) {
    this()
    this.name = name
  }

  def this(name: String, age: Int) {
    this(name)
    this.age = age
  }

  override def toString = s"Person(name=$name, age=$age)"
}

class Person2 {
  @BeanProperty var name: String = _;
  @BeanProperty var age: Int = _;

  override def toString = s"Person2($name, $age)"
}

class Person3(name: String) {
  var age =1
  override def toString = s"Person3($name, $age)"
}
