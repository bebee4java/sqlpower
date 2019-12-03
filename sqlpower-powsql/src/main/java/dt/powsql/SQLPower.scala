package dt.powsql

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/**
  *
  * Created by songgr on 2019/12/03.
  */

@SpringBootApplication
@ComponentScan(basePackages = Array("dt.powsql.rest"))
class SQLPower

object SQLPower extends App {
  SpringApplication.run(classOf[SQLPower])
}
