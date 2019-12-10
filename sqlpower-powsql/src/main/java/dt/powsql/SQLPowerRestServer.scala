package dt.powsql

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.web.server.{ConfigurableWebServerFactory, WebServerFactoryCustomizer}
/**
  *
  * Created by songgr on 2019/12/03.
  */

@SpringBootApplication
@ComponentScan(basePackages = Array("dt.powsql.rest"))
class SQLPowerRestServer extends WebServerFactoryCustomizer[ConfigurableWebServerFactory]{
  override def customize(factory: ConfigurableWebServerFactory): Unit = {
    factory.setPort(SQLPowerRestServer.port)
  }
}

object SQLPowerRestServer {
  private var port:Int = _

  def run:Unit = {
    SpringApplication.run(classOf[SQLPowerRestServer])
  }

  def setPort(port:Int):SQLPowerRestServer.type= {
    this.port = port
    this
  }
}
