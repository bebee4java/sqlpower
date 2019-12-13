package dt.sql.power.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.server.{ConfigurableWebServerFactory, WebServerFactoryCustomizer}
import org.springframework.context.annotation.ComponentScan
/**
  *
  * Created by songgr on 2019/12/03.
  */

@SpringBootApplication
@ComponentScan(basePackages = Array("dt.sql.power.core.rest"))
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
