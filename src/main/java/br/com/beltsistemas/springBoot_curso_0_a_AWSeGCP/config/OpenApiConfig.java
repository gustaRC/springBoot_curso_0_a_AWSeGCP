package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean // Bean é um objeto instanciado que é inicializado e executado pelo SringBoot
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("REST API's RESTful from 0 with Java, Spring Boot, Kubernetes and Docker - Course")
                .version("v1")
                .description("Some description about your API")
                .termsOfService("https://beltsistemas.com.br/")
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://belt-sistemas.com.br/")
                )
            );
    }
}
