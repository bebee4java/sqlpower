package dt.sql.power.runtime

trait SQLRuntime {

  def startRuntime: SQLRuntime

  def awaitTermination

  def params: Map[Any, Any]

}
