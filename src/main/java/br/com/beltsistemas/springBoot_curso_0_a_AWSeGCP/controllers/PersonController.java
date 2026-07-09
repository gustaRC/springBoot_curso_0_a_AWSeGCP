package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service; // == private PersonService service = new PersonService();

    @RequestMapping(
            method = RequestMethod.GET,
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> findAll() {
        return service.findAll();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(
            @PathVariable String id //@PathVariable indica a necessidade de preenchimento do caminho da URL: '/person/{id}'
    ) {
        return service.findById(Long.parseLong(id));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = MediaType.APPLICATION_JSON_VALUE, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public Person create(
            @RequestBody Person person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.create(person);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = MediaType.APPLICATION_JSON_VALUE, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public Person update(
            @RequestBody Person person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.update(person);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(
            @PathVariable String id
    ) {
        service.delete(Long.parseLong(id));
    }
}
