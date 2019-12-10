package dt.powsql.utils

import java.io.IOException
import java.net.{DatagramSocket, InetAddress, ServerSocket, UnknownHostException}

object NetworkUtil {

  val host = getHost

  def getHost: String = {
    try {
      InetAddress.getLocalHost.getHostName
    } catch {
      case e:UnknownHostException =>
        e.printStackTrace()
        null
    }
  }

  def available(port: Int, minPort: Int, maxPort: Int): Boolean = {
    if (port < minPort || port > maxPort)
      throw new IllegalArgumentException("Invalid start port: " + port)
    var ss:ServerSocket = null
    var ds:DatagramSocket = null
    try {
      ss = new ServerSocket(port)
      ss.setReuseAddress(true)
      ds = new DatagramSocket(port)
      ds.setReuseAddress(true)
      return true
    } catch {
      case e:IOException => // nothing to do
    } finally {
      try {
        if (ds != null)
          ds.close()
        if (ss != null)
          ss.close()
      } catch {
        case e:Exception => // nothing to do
      }
    }
    false
  }

}
