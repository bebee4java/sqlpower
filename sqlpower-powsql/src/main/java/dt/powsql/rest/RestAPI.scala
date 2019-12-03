package dt.powsql.rest

import dt.powsql.log.Logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}


/**
  *
  * Created by songgr on 2019/12/03.
  */

@Controller
class RestAPI {
  val logger: Logger = Logger.getInstance(classOf[RestAPI])

  @RequestMapping(value = Array("/"))
  def index() : String = {
    "index"
  }


  @RequestMapping(value = Array("/hello"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello() : String = {
    "hello developer (^_^)"
  }

}
