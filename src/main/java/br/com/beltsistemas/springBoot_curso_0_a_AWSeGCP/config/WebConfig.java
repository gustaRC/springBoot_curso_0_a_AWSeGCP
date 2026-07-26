package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.yaml.JacksonYamlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.dataformat.yaml.YAMLMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final YAMLMapper yamlMapper;

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
