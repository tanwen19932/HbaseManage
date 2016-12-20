package util

import java.io._
import java.util.Properties

import org.apache.log4j.Logger

/**
  * Created by didi on 16/9/30.
  */
object PropUtil {

  private val logger:Logger=Logger.getLogger("map_offline")

  def loadProperties(filename: String): Properties={
    streamToProperties(loadFlow(filename))
  }

  def loadFile(filename: String): InputStream={
    loadFlow(filename)
  }

  private def loadFlow(filename: String): InputStream={
    var is=loadFileFromSubmitDir(filename)
    if(!is.isDefined){
      is=loadFileFromWorkDir(filename)
      if(!is.isDefined){
        is=loadFileFromJar(filename)
        if(!is.isDefined){
          throw new FileNotFoundException(s"File ${filename} not found")
        }
        logger.info(s"load file [${filename}] from jar")
        return is.get
      }
      logger.info(s"load file [${filename}] from workDir")
      return is.get
    }
    logger.info(s"load file [${filename}] from submitDir")
    is.get
  }

  private def streamToProperties(is: InputStream)={
    val prop=new Properties()
    try{
      prop.load(is)
    }catch{
      case ex: IOException=>{
        logger.error(s"failed load properties", ex)
      }
    }finally {
      is.close()
    }
    prop
  }

  def loadFileFromSubmitDir(filename: String): Option[InputStream]={
    val file=new File(filename)
    if(!file.exists()) return None
    val fis=new FileInputStream(filename)
    logger.info(s"load ${filename} from submit directory [${file.getPath}]")
    Some(fis)
  }

  def loadFileFromWorkDir(filename: String): Option[InputStream]={
    val dirs=PropUtil.getClass.getProtectionDomain.getCodeSource.getLocation.getPath.split(File.separator)
    val path=dirs.take(dirs.length-1).mkString(File.separator)+File.separator+filename
    val file=new File(path)
    if(!file.exists()) return None
    val fis=new FileInputStream(file)
    logger.info(s"load ${filename} from work directory [${file.getPath}]")
    Some(fis)
  }

  def loadFileFromJar(filename: String): Option[InputStream]={
    val ins=PropUtil.getClass.getClassLoader.getResourceAsStream(filename)
    if(ins==null) return None
    Some(ins)
  }

  def main(args: Array[String]) {
    loadFile("jobSchedule.xml")
  }
}
