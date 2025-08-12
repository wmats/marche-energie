package fr.test.marche_energie.application.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Application marche-energie",
            version = "1.0",
            description = "API documentation"),
    servers =
        @Server(
            url = "${server.servlet.context-path:/}",
            description = "URL par défaut (relative)"))
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi marcheEnergieApi() {
    return GroupedOpenApi.builder()
        .group("Marché énergie")
        .pathsToMatch("/**")
        .packagesToScan("fr.test.marche_energie")
        .build();
  }
}
