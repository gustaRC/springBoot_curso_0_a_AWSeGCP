package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service // utilizado para tratamentos de regras de negócio separadamente do Controller (função definida somente para controle de Rotas)
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName()); //aprofundaremos no Logger mais para frente

    public Person findById(Long id) {
        logger.info("Finding one person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Biricubicu");
        person.setLastName("Siricuticu");
        person.setAddress("Anápolis - Goiás - Brasil");
        return person;
    }
}
