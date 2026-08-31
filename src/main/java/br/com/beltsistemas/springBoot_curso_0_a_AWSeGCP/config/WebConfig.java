package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.yaml.JacksonYamlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final YAMLMapper yamlMapper;

/*  Fluxo da Utilização de Atribuição do atributo corsOriginPatterns:
        1. O Spring irá ler o application.yaml;
        2. Será identificado o valor no caminho: cors > originPatterns;
        3. Com a configuração usando a Annotation @Value, o valor desse
           caminho será armazenado no atributo corsOriginPatterns;
        4. Caso não possua nada neste caminho no application.yaml, será
           aplicado o valor após os dois pontos (:), considerado o valor
           'default'. */
    @Value("${cors.originPatterns:http://www.url_default.com}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);

        String[] allowedOrigins = corsOriginPatterns.split(",");

        registry.addMapping("/**") // configuração de CORS será aplicada em TODA A APLICAÇÃO
                .allowedOrigins(allowedOrigins)
//              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // configurar métodos http específicos
                .allowedMethods("*") // configurar para todos os métodos específicos
                .allowCredentials(true);

    }

    public WebConfig(YAMLMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//      Via QUERY PARAM - "_URI?mediaType=yaml"
        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true) // ignora no Header o parâmetro "Accept", caso contrário o valor de "Accept" será persistido.
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);

//      Via HEADER PARAM - "_URI" com Accept: application/yaml
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false) // ignora no Header o parâmetro "Accept", caso contrário o valor de "Accept" será persistido.
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);
    }

//  Método necessário para fazer o formato YAML funcionar
    @Override
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        builder.withYamlConverter(new JacksonYamlHttpMessageConverter(yamlMapper));
    }
}
