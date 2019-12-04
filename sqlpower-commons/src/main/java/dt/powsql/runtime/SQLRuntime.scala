package dt.powsql.runtime

trait SQLRuntime {

  def startRuntime: SQLRuntime

  def awaitTermination

  def params: Map[Any, Any]

}
