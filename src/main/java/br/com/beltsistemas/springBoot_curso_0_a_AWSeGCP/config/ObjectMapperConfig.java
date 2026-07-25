package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

//      vejo utilidade em fazer padrões de filtragem, por exemplo: uma exibição de atributos para usuários logados e outra para usuários deslogados
        SimpleFilterProvider filters = new SimpleFilterProvider()
//                          adicionamos atributos que não queremos que sejam enviados, por exemplo: password, lastName, etc
                .addFilter("PersonFilter", SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData"))
                .addFilter("Test", SimpleBeanPropertyFilter.serializeAllExcept("attributeTest"));

        mapper.rebuild().filterProvider(filters);
        return mapper; /*
        RECOMENDAÇÃO DO CLAUDE:
        return JsonMapper.builder()
            .filterProvider(filters)
            .build(); */
    }
}
