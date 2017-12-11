package in.phani.springboot.config;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.google.common.collect.Lists;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by panikiran on 17.03.17.
 */
@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
public class SwaggerConfig {

  @Bean
  public Docket productDetailApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .select()
        .paths(PathSelectors.regex("(?!/error).+")).paths(PathSelectors.regex("(?!/internal).+"))
        .build();
  }

  private ApiInfo apiInfo() {
    final Contact contact = new Contact("Panikiran Periyapatna",
        "https://github.com/pvpkiran/datamongo", "pvpkiran@gmail.com");
    return new ApiInfo("DataMongoDemo",
        "Microservice demostrating data / embedded mongo.", "0.1-SNAPSHOT", null,
        contact, null, null, Lists.newArrayList());
  }
}
