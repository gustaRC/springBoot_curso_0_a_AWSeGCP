package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Informa ao Spring que nesta classe consta configurações importantes para a aplicação!
// Ou seja, irá executar os métodos @Bean desta classe sempre que a aplicação for inicializada
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

//      Via EXTENSION - "_URI.xml or _URI.json" -> Deprecated On SpringBoot 2.6

//      Via QUERY PARAM - "_URI?mediaType=xml"
        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true) // ignora no Header o parâmetro "Accept", caso contrário o valor de "Accept" será persistido.
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON) // valor padrão do mediaType
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);

//      Via HEADER PARAM - "_URI"
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false) // ignora no Header o parâmetro "Accept", caso contrário o valor de "Accept" será persistido.
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON) // valor padrão do Accept
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
