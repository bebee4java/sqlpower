package dt.sql.power.core.rest

import java.util

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, ResponseBody}
import java.util.{Map => JMap}
import dt.sql.power.log.Logging
import dt.sql.power.platform.PlatformManager
import io.swagger.annotations.{ApiImplicitParam, ApiImplicitParams, ApiOperation}


/**
  *
  * Created by songgr on 2019/12/03.
  */

@Controller
class RestAPI extends RequestHolder with Logging {

  @RequestMapping(value = Array("/hello"), method = Array(RequestMethod.GET))
  @ApiOperation(
    value = "welcome",
    notes = "SQLPower say hello to you"
  )
  @ApiImplicitParams(value = Array(
    new ApiImplicitParam(name = "username", value = "用户名",
      dataType = "string", defaultValue = "developer")
  ))
  @ResponseBody
  def hello(@RequestParam username:String) : String = {
    s"hello $username (^_^)"
  }

  @RequestMapping(value = Array("/run/script"),
    method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def run : Array[JMap[String,Any]] = {
    logInfo("run script...")

    val sql = params("sql")
    logInfo(s"script:\n $sql")

    Thread.sleep(4000)

    val map = new util.HashMap[String,Any]()
    map.put("id",1)
    map.put("name","zs")
    map.put("doc",
      """
        |Command: bash -c source activate mlflow-odep_325_job-ce7b987f86314a58924b382fd72d7d0f728bdf85 && python pythonScript.py
        |TaskDirectory: /tmp/__mlsql__/e44ccf5e-8c72-41f5-af1d-fb96394ae95b/mlsqlrun/mlsql-python-project
        |DataDirectory: /tmp/__mlsql__/471200f0-1f9b-4f00-9390-794244aa5ee1/data/0
        |ModelDirectory: /tmp/__mlsql__/dc2522e6-b0ad-4b95-9254-be888bfaa15d/models/0
      """.stripMargin)


    logInfo("run script succeed!!")
    Array[JMap[String,Any]](map, map, map)

  }

  def runtime = PlatformManager.getRuntime

}
