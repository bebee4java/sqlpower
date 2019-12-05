package dt.powsql.rest

import java.util

import dt.powsql.log.Logging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import java.util.{Map => JMap}

import dt.powsql.platform.PlatformManager


/**
  *
  * Created by songgr on 2019/12/03.
  */

@Controller
class RestAPI extends RequestHolder with Logging {

  @RequestMapping(value = Array("/"))
  def index : String = {
    logInfo("jump to index.html")
    "index"
  }


  @RequestMapping(value = Array("/hello"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello : String = {
    "hello developer (^_^)"
  }

  @RequestMapping(value = Array("/run/script"),
    method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def run : Array[JMap[String,Any]] = {
    logInfo("run script...")

    val sql = params("sql")
    logInfo(s"script:\n $sql")


    val map = new util.HashMap[String,Any]()

    logInfo("run script succeed!!")
    Array[JMap[String,Any]](map)

  }

  def runtime = PlatformManager.getRuntime

}
