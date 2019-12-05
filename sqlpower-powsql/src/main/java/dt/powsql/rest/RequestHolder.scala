package dt.powsql.rest

import javax.servlet.http.HttpServletRequest
import javax.servlet.{ServletRequestEvent, ServletRequestListener}

/**
  *
  * Created by songgr on 2019/12/05.
  */
class RequestHolder extends ServletRequestListener {

  private val httpServletRequestHolder = new ThreadLocal[HttpServletRequest]
  private val requestMap = new ThreadLocal[Map[String,String]]

  override def requestInitialized(requestEvent: ServletRequestEvent): Unit = {
    val request = requestEvent.getServletRequest.asInstanceOf[HttpServletRequest]
    httpServletRequestHolder.set(request)
  }

  override def requestDestroyed(sre: ServletRequestEvent): Unit = {
    httpServletRequestHolder.remove()
  }

  private[this] def getHttpServletRequest: HttpServletRequest = httpServletRequestHolder.get


  import scala.collection.JavaConverters._
  def params: Map[String, String] = {
    if (requestMap.get() == null) {
      this.synchronized {
        val map = getHttpServletRequest.getParameterMap
          .asScala.map{
          kv =>
            (kv._1, kv._2(0))
        }.toMap
        if (requestMap.get() == null) {
          requestMap.set(map)
        }
      }
    }
    requestMap.get()
  }

  def hasParam(key: String): Boolean = params.contains(key)

  def getParam(key: String) = params.get(key)

  def getParam(key: String, defaultValue: String): String = {
    val value = params.get(key).get
    if (value == null || "" == value) defaultValue else value
  }

  def paramAsBoolean(key: String, defaultValue: Boolean): Boolean = if(hasParam(key))
    "true" == getParam(key, "").toLowerCase
  else defaultValue

}

