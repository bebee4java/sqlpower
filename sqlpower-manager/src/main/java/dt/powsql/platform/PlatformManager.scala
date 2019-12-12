package dt.powsql.platform

import java.util.concurrent.atomic.AtomicReference

import dt.powsql.common.ParamsUtil
import dt.powsql.log.Logging
import dt.powsql.constants.Constants
import dt.powsql.runtime.SQLRuntime

/**
  * manage platform（get spark/flink runtime, start rest server）
  * Created by songgr on 2019/12/03.
  */
class PlatformManager extends Logging {
  self =>
  val config = new AtomicReference[ParamsUtil]()

  def run(_params: ParamsUtil, reRun: Boolean = false) = {

    if (!reRun) {
      config.set(_params)
    }

    val params = config.get()


    try {
      val runtime = PlatformManager.getRuntime
    } catch {
      case e=>
        logError(e.getMessage, e)
        System.exit(-1)
    }

  }

  PlatformManager.setLastInstantiatedContext(self)
}

object PlatformManager {
  private val INSTANTIATION_LOCK = new Object()
  @transient private val lastInstantiatedContext = new AtomicReference[PlatformManager]()

  def getOrCreate: PlatformManager = {
    INSTANTIATION_LOCK.synchronized {
      if (lastInstantiatedContext.get() == null) {
        new PlatformManager()
      }
    }
    lastInstantiatedContext.get()
  }

  private[platform] def setLastInstantiatedContext(platformManager: PlatformManager): Unit = {
    INSTANTIATION_LOCK.synchronized {
      lastInstantiatedContext.set(platformManager)
    }
  }

  private[platform] def clearLastInstantiatedContext(): Unit = {
    INSTANTIATION_LOCK.synchronized {
      lastInstantiatedContext.set(null)
    }
  }

  def createRuntimeByPlatform(name: String, params: Map[Any, Any]) = {
    Class.forName(name).
      getMethod("getOrCreate", classOf[Map[Any, Any]]).
      invoke(null, params).asInstanceOf[SQLRuntime]
  }

  def getRuntime: SQLRuntime = {
    val params: Map[String, String] = getOrCreate.config.get().getParamsMap
    val tempParams = params.map(kv => (kv._1.asInstanceOf[Any], kv._2.asInstanceOf[Any]))


    val platformName = params.getOrElse(Constants.platform, SPARK)
    val runtime = createRuntimeByPlatform(platformNameMapping(platformName), tempParams)

    runtime
  }


  def SPARK = "spark"
  def FLINK = "flink"

  def platformNameMapping = Map[String, String](
    SPARK -> "dt.powsql.runtime.SparkRuntime",
    FLINK -> "dt.powsql.runtime.FlinkRuntime"
  )



}
