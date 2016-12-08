import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}

/*
 * 需求：用scala操作hdfs，但是又不想用RDD。
 * http://bleibinha.us/blog/2013/09/accessing-the-hadoop-distributed-filesystem-hdfs-with-scala
 * http://www.linuxidc.com/Linux/2014-04/100545.htm HDFS——如何列出目录下的所有文件
 */
object Hdfs {
  def ls(fileSystem:FileSystem,path:String)=
  {
    println("list path:"+path)
    val fs = fileSystem.listStatus(new Path(path))
    val listPath = FileUtil.stat2Paths(fs)
    for( p <- listPath)
    {
      println(p)
    }
    println("----------------------------------------")
  }
  def main(args: Array[String]) {
    val conf = new Configuration()
    //val hdfsCoreSitePath = new Path("core-site.xml")
    // val hdfsHDFSSitePath = new Path("hdfs-site.xml")
    //conf.addResource(hdfsCoreSitePath)
    //conf.addResource(hdfsHDFSSitePath)
    println(conf)//Configuration: core-default.xml, core-site.xml
    //根据这个输出,在这个程序进来之前,conf已经被设置过了

    //目前我知道,定位具体的hdfs的位置,有两种方式
    //一种是在conf配置,一个域名可以绑定多个ip.我们通过这个域名来定位hdfs.
    //另一种是在调用FileSystem.get时指定一个域名或者一个ip,当然仅限一个.

    val fileSystem = FileSystem.get(conf)
    //如果conf设置了hdfs的host和port,此处可以不写
    //hadoop的配置都是一层一层的,后面的会覆盖前面的.

    //String HDFS="hdfs://localhost:9000";
    //FileSystem hdfs = FileSystem.get(URI.create(HDFS),conf);
    //这种写法 只能用一个ip或者域名了.不推荐.

    ls(fileSystem,"/")
    ls(fileSystem,".")
    ls(fileSystem,"svd")

  }
}
