package tw.spark.learning._9Files

import scala.io.Source

/**
  * @author TW
  * @date TW on 2016/12/5.
  */
object FilesGet {
  def main(args: Array[String]): Unit = {
    val filePath = "myFile";
    val file = Source.fromFile(filePath, "utf-8")

    for (line <- file.getLines()) {
      print(line)
      val spilts = line.split("\t")
      val mi = spilts(1).toDouble
      val pair = (filePath, spilts(1), mi)
    }
    file.close()
  }
}
