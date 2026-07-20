package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.PersonDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v2.PersonDTOV2;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.ResourceNotFoundException;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper.custom.PersonMapper;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper.ObjectMapper.parseListObjects;
import static br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper.ObjectMapper.parseObject;

@Service // utilizado para tratamentos de regras de negócio separadamente do Controller (função definida somente para controle de Rotas)
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper converter;

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName()); //aprofundaremos no Logger mais para frente

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");

//      Person -> PersonDTO.class
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

//      Person -> PersonDTO.class
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one person!");

//      PersonDTO -> Person.class
        Person entity = parseObject(person, Person.class);

//      Person -> PersonDTO.class
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Creating one person!");

//      PersonDTOV2 -> Person.class
        Person entity = parseObject(person, Person.class);

//      Person -> PersonDTOV2.class
        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one person!");

//      PersonDTO -> Person.class
//      poderia ser evitado essa conversão, contudo... visando reutilizar função findById usaremos dessa forma!
        Person entity = parseObject(findById(person.getId()), Person.class); // verificação se a Person existe para edição + linkagem com a entidade/registro no banco de dados

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());

//      Person -> PersonDTO.class
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        PersonDTO entity = findById(id);

        repository.deleteById(entity.getId());
    }
}
