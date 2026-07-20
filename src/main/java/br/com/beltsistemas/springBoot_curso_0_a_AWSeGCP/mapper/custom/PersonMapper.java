package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper.custom;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v2.PersonDTOV2;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public static PersonDTOV2 convertEntityToDTO(Person entity) {
        PersonDTOV2 dto = new PersonDTOV2();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAddress(entity.getAddress());
        dto.setBirthDate(new Date()); // Em um cenário REAL: criaríamos o atributo/coluna na Entidade Person permitindo a princípio o seu preenchimento com dados nulos;
        return dto;
    }

    public static Person convertDTOToEntity(PersonDTOV2 dto) {
        Person entity = new Person();

        entity.setId(dto.getId());
        entity.setFirstName(entity.getFirstName());
        entity.setLastName(entity.getLastName());
        entity.setAddress(entity.getAddress());
        //entity.setBirthDate(new Date()); // Em um cenário REAL: criaríamos o atributo/coluna na Entidade Person permitindo a princípio o seu preenchimento com dados nulos;
        return entity;
    }
}
