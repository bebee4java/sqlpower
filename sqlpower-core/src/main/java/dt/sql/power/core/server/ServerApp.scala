package dt.sql.power.core.server

import dt.sql.power.common.ParamsUtil
import dt.sql.power.constants.Constants
import dt.sql.power.log.Logging
import dt.sql.power.utils.NetworkUtil
import dt.sql.power.core.SQLPowerRestServer
import dt.sql.power.platform.PlatformManager

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

    }

    PlatformManager.getOrCreate.run(params)

  }

  def startRestServer(port:Int) = {
    SQLPowerRestServer.setPort(port).run
    logInfo(
      s"""
        |SQL Power rest api started http://${NetworkUtil.host}:$port
        |SQL Power rest api swagger ui url is http://${NetworkUtil.host}:$port/swagger-ui.html""".stripMargin)
  }




}
