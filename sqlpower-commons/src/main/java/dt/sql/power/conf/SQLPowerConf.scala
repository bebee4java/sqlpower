package dt.sql.power.conf

import dt.sql.power.constants.Constants
import dt.sql.power.log.Logging

import scala.collection.mutable.ArrayBuffer

/**
  *
  * Created by songgr on 2019/12/04.
  */
object SQLPowerConf extends Logging{

 private [this] var sqlPowerConf:Map[String, Any] = _

 private[this] object SqlPowerConfigBuilder {
  val confs = new ArrayBuffer[(String, Any)]()
  def apply(map : Map[Any, Any]) = {
   map.filter(f => f._1.toString.startsWith("sqlpower"))
     .foreach{
      f =>
       val conf = (f._1.toString, f._2)
       confs += conf
     }
   sqlPowerConf = Map[String,Any](confs:_*)
  }
 }

 def createConfigReader(settings: Map[Any, Any]) = {
  SqlPowerConfigBuilder(settings)
 }

 lazy val MASTER = sqlPowerConf.get(Constants.master)
 lazy val APPNAME = sqlPowerConf.get(Constants.appName)
 lazy val ENABLEHIVE = sqlPowerConf.get(Constants.enableHive).isDefined &&
   "true" == sqlPowerConf.get(Constants.enableHive).get.asInstanceOf[String]



 def show(conf: Map[String, String]) {
  val keyLength = conf.keys.map(_.size).max
  val valueLength = conf.values.map(_.size).max
  val header = "-" * (keyLength + valueLength + 3)
  logInfo("SQL Power server start with configuration!")
  logInfo(header)
  conf.map {
   case (key, value) =>
    val keyStr = key + (" " * (keyLength - key.size))
    val valueStr = value + (" " * (valueLength - value.size))
    s"|${keyStr}|${valueStr}|"
  }.foreach(line => {
   logInfo(line)
  })
  logInfo(header)
 }

}
