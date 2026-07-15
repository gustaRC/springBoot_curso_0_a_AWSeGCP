package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault(); // irá mapear entidade para view (objeto destino -> objeto envio)

    // Parse/Conversão: Entidade -> DTO & DTO -> Entidade
    public static <Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination) {
        return mapper.map(origin, destination);
    }

    // Parse/Conversão: Lista Entidade -> Lista DTO & Lista DTO -> Lista Entidade
    public static <Origin, Destination> List<Destination> parseListObjects(List<Origin> origin, Class<Destination> destination) {
        List<Destination> destinationList = new ArrayList<Destination>();

        for (Object o : origin) {
            destinationList.add(mapper.map(o, destination));
        }

        return destinationList;
    }
}
