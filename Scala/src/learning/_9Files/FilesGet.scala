package tw.spark.learning._9Files

import scala.io.Source

/**
  * @author TW
  **/
object FilesGet {
  def main(args: Array[String]): Unit = {
    val filePath = "/Users/TW/ja_all/all";
    val file = Source.fromFile(filePath, "utf-8")
    val pairs = file.getLines().flatMap(line=> line.split("\r")).map(pairStr=>(pairStr.split(" ")(0), pairStr.split(" ")(1).toInt))
    for(pair<-pairs){
      println(pair)
    }
    file.close()
  }
}
