package dt.sql.power.runtime

import java.util.concurrent.atomic.AtomicReference

import dt.sql.power.conf.SQLPowerConf
import dt.sql.power.log.Logging
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  *
  * Created by songgr on 2019/12/04.
  */
class SparkRuntime(_params:Map[Any, Any]) extends SQLRuntime with Logging{

  def name = "spark"

  SQLPowerConf.createConfigReader(_params)

  val sparkSession: SparkSession = createRuntime


  def createRuntime = {
    logInfo("create Spark Runtime....")

    val conf = new SparkConf()
    params.filter(f =>
      f._1.toString.startsWith("spark.") ||
        f._1.toString.startsWith("hive.")
    ).foreach { f =>
      conf.set(f._1.toString, f._2.toString)
    }

    conf.setAppName(SQLPowerConf.APPNAME.get.toString)

    if (SQLPowerConf.MASTER.isDefined) {
      conf.setMaster(SQLPowerConf.MASTER.get.toString)
    }

    val sparkSession = SparkSession.builder().config(conf)

    if (SQLPowerConf.ENABLEHIVE) {
      sparkSession.enableHiveSupport()
    }

    SQLPowerConf.show(params.map(kv => (kv._1.toString, kv._2.toString)))

    val ss = sparkSession.getOrCreate()
    logInfo("Spark Runtime created!!!")
    ss
  }

  SparkRuntime.setLastInstantiatedContext(this)

  override def startRuntime: SQLRuntime = this

  override def awaitTermination: Unit = ???

  override def params: Map[Any, Any] = _params
}

object SparkRuntime {
  private val INSTANTIATION_LOCK = new Object()

  /**
    * Reference to the last created SQLContext.
    */
  @transient private val lastInstantiatedContext = new AtomicReference[SparkRuntime]()


  def getOrCreate(params: Map[Any, Any]): SparkRuntime = {
    INSTANTIATION_LOCK.synchronized {
      if (lastInstantiatedContext.get() == null) {
        new SparkRuntime(params)
      }
    }
    lastInstantiatedContext.get()
  }

  private def clearLastInstantiatedContext(): Unit = {
    INSTANTIATION_LOCK.synchronized {
      lastInstantiatedContext.set(null)
    }
  }

  private def setLastInstantiatedContext(sparkRuntime: SparkRuntime): Unit = {
    INSTANTIATION_LOCK.synchronized {
      lastInstantiatedContext.set(sparkRuntime)
    }
  }

}
