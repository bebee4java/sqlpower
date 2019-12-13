package dt.sql.power.common

object NumberUtil {


  def getIntValue( obj:Object, defaultValue:Int) = {
    if (obj == null) {
      defaultValue
    } else {
      try {
        Integer.parseInt(obj.toString)
      } catch {
        case e:Exception => defaultValue
      }
    }

  }

}
