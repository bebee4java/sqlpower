package dt.powsql.core.server

import dt.powsql.SQLPowerRestServer
import dt.powsql.common.ParamsUtil
import dt.powsql.constants.Constants
import dt.powsql.log.Logging
import dt.powsql.platform.PlatformManager
import dt.powsql.utils.NetworkUtil

object ServerApp extends Logging {

  def main(args: Array[String]): Unit = {

    val params = new ParamsUtil(args)

    require(params.hasParam(Constants.appName), "Application name must be set")

    if (params.getBooleanParam(Constants.rest, false)) {
      val restPort = params.getIntParam(Constants.restPort)

      if(!NetworkUtil.available(restPort,1024,65535)) {
        throw new IllegalArgumentException(s"port: $restPort already in use")
      }

      startRestServer(restPort)
      logInfo(s"SQL Power rest api started http://${NetworkUtil.host}:$restPort")
    }

    PlatformManager.getOrCreate.run(params)

  }

  def startRestServer(port:Int) = {
    SQLPowerRestServer.setPort(port).run
  }




}
