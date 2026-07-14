package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.ResourceNotFoundException;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service // utilizado para tratamentos de regras de negócio separadamente do Controller (função definida somente para controle de Rotas)
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName()); //aprofundaremos no Logger mais para frente

    public List<Person> findAll() {
        logger.info("Finding all people!");

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one person!");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person) {
        logger.info("Creating one person!");
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person!");

        Person entity = findById(person.getId()); // verificação se a Person existe para edição + linkagem com a entidade/registro no banco de dados

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        Person entity = findById(id);

        repository.deleteById(entity.getId());
    }
}
