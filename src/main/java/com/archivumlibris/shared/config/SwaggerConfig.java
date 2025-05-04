package com.archivumlibris.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI archivumLibrisOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Archivum Libris API")
            .description("RESTful API for book management")
            .version("1.0.0")
            .contact(new Contact()
                .name("Bruno Lira")
                .url("https://github.com/brunoliratm/ArchivumLibris-API")
                .email("bmagnoserver@gmail.com"))
            .license(new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT")));
  }
}
