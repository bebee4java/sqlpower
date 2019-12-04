package dt.powsql.core.server

object LocalSparkServerApp {

  def main(args: Array[String]): Unit = {

    ServerApp.main(
      Array(
        "-sqlpower.master", "local[*]",
        "-sqlpower.name", "sqlpower",
        "-sqlpower.rest", "true",
        "-sqlpower.platform", "spark"
      )
    )
  }

}

