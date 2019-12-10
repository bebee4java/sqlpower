package dt.powsql.rest.config

import org.springframework.context.annotation.{Bean, Configuration}
import springfox.documentation.builders.{ApiInfoBuilder, PathSelectors, RequestHandlerSelectors}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
  *
  * Created by songgr on 2019/12/10.
  */

@Configuration
@EnableSwagger2
class SwaggerConfig {

  @Bean
  def createRestApi: Docket = new Docket(DocumentationType.SWAGGER_2)
    .apiInfo(apiInfo)
    .select.apis(RequestHandlerSelectors.basePackage("dt.powsql.rest"))
    .paths(PathSelectors.any)
    .build

  private def apiInfo = new ApiInfoBuilder()
    .title("SQLPower api")
    .description("SQL Power stable restful api")
    .version("1.0")
    .build

}
