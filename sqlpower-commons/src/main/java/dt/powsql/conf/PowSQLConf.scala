package dt.powsql.conf

import dt.powsql.constants.Constants
import dt.powsql.log.Logging

import scala.collection.mutable.ArrayBuffer

/**
  *
  * Created by songgr on 2019/12/04.
  */
object PowSQLConf extends Logging{

 private [this] var powSQLConf:Map[String, Any] = _

 private[this] object PowSQLConfigBuilder {
  val confs = new ArrayBuffer[(String, Any)]()
  def apply(map : Map[Any, Any]) = {
   map.filter(f => f._1.toString.startsWith("sqlpower"))
     .foreach{
      f =>
       val conf = (f._1.toString, f._2)
       confs += conf
     }
   powSQLConf = Map[String,Any](confs:_*)
  }
 }

 def createConfigReader(settings: Map[Any, Any]) = {
  PowSQLConfigBuilder(settings)
 }

 lazy val MASTER = powSQLConf.get(Constants.master)
 lazy val APPNAME = powSQLConf.get(Constants.appName)
 lazy val ENABLEHIVE = powSQLConf.get(Constants.enableHive).isDefined &&
   "true" == powSQLConf.get(Constants.enableHive).get.asInstanceOf[String]



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
