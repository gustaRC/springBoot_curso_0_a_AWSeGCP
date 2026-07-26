package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.boot.jackson.autoconfigure.XmlMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;
import tools.jackson.dataformat.yaml.YAMLMapper;

@Configuration
public class ObjectMapperConfig {

    private SimpleFilterProvider buildFilters() {
//      vejo utilidade em fazer padrões de filtragem, por exemplo: uma exibição de atributos para usuários logados e outra para usuários deslogados
        return new SimpleFilterProvider()
//                          adicionamos atributos que não queremos que sejam enviados, por exemplo: password, lastName, etc
                .addFilter("PersonFilter", SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData"))
                .addFilter("Test", SimpleBeanPropertyFilter.serializeAllExcept("attributeTest"));
    }

    /*
    Necessário esses três métodos devido a erro de configuração do JSON/XML pelo Spring:
    ObjectMapperConfig.java — O problema original era criar um ObjectMapper/XmlMapper manualmente como @Bean,
    o que substituía o auto-configurado pelo Spring Boot. Isso causava:
        - Para JSON: o mapper manual não tinha todos os módulos/configurações que o Spring Boot configuraria automaticamente;
        - Para XML: o mapper manual era ObjectMapper puro sem suporte a XML.
     */
    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.filterProvider(buildFilters());
    }

    @Bean
    public XmlMapperBuilderCustomizer xmlCustomizer() {
        return builder -> builder.filterProvider(buildFilters());
    }

    @Bean
    public YAMLMapper yamlMapper() {
        return YAMLMapper.builder()
                .filterProvider(buildFilters())
                .build();
    }
}
