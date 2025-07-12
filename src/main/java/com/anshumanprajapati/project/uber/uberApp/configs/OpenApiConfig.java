package com.anshumanprajapati.project.uber.uberApp.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI uberAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Uber App API")
                        .description("API documentation for Uber-like application")
                        .version("v1.0"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("uber-public")
                .pathsToMatch("/**")
                .build();
    }
}
