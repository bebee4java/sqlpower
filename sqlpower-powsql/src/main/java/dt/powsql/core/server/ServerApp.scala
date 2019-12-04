package dt.powsql.core.server

import dt.powsql.common.ParamsUtil
import dt.powsql.constants.Constants
import dt.powsql.platform.PlatformManager

object ServerApp {

  def main(args: Array[String]): Unit = {

    val params = new ParamsUtil(args)

    require(params.hasParam(Constants.appName), "Application name must be set")

    PlatformManager.getOrCreate.run(params)

  }

}
