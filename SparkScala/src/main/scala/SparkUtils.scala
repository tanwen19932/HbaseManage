import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.{Date, Properties}

/**
  * Created by Administrator on 2016/10/13.
  */
object SparkUtils {

  /**
    * 根据IP获取cityID
    *
    * @param ip
    * @param iptable 排序IP
    * @param cityMap 排序IP对应Map
    * @return
    */
  def getCityId(ip: Long, iptable: Array[Long], cityMap: Map[Long, String]) = {
    val index = binaryGetCityId(ip, iptable)
    cityMap(index)
  }

  /**
    * 二分查找IP库区间文件
    *
    * @param ip
    * @param iptable
    * @return
    */
  def binaryGetCityId(ip: Long, iptable: Array[Long]): Long = {
    var low = 0;
    var high = iptable.length - 1;
    while (low <= high) {
      var middle = low + ((high - low) >> 1);

      if (ip <= iptable(middle)) {
        if (middle == 0) {
          return middle
        } else if (ip > iptable(middle - 1)) {
          return middle
        } else {
          high = middle - 1
        }
      } else {
        low = middle + 1;
      }
    }
    return -1
  }


  /**
    * ip字符串转Long
    *
    * @param ip
    * @return
    */
  def ipToLong(ip: String): Long = {
    val ip_num = for (elem <- ip.split('.')) yield elem.toInt
    def base: Long = 256
    base * (base * (base * ip_num(0) + ip_num(1)) + ip_num(2)) + ip_num(3)
  }

  /**
    *  读取属性文件
    *
    * @param key
    */
  def loadProperties(key: String): Unit = {
    val properties = new Properties()
    val path = Thread.currentThread().getContextClassLoader.getResource("application.properties").getPath //文件要放到resource文件夹下
    properties.load(new FileInputStream(path))
    println(properties.getProperty(key))
  }



  def main(args: Array[String]) {
    val d = new Date();
    val myFmt = new SimpleDateFormat("mmss");
    val mmss = myFmt.format(d);
    val flag = "0000".equals(mmss);
    println(flag)
  }


}
