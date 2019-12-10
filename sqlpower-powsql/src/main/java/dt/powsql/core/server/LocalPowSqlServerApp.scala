package dt.powsql.core.server

object LocalPowSqlServerApp {

  def main(args: Array[String]): Unit = {

    ServerApp.main(
      Array(
        "-sqlpower.master", "local[*]",
        "-sqlpower.name", "sqlpower",
        "-sqlpower.rest", "true",
        "-sqlpower.platform", "spark",
        "-sqlpower.rest.port", "3000"
      )
    )
  }

}

