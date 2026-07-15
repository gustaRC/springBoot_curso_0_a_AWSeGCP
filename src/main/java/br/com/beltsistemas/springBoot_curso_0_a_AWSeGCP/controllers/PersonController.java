package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.PersonDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service; // == private PersonService service = new PersonService();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE /* produces -> dados ENVIADOS DA a API (response) */ )
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE /* produces -> dados ENVIADOS DA a API (response) */
    )
    public PersonDTO findById(
            @PathVariable String id //@PathVariable indica a necessidade de preenchimento do caminho da URL: '/person/{id}'
    ) {
        return service.findById(Long.parseLong(id));
    }

    @PostMapping(
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = MediaType.APPLICATION_JSON_VALUE, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public PersonDTO create(
            @RequestBody PersonDTO person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.create(person);
    }

    @PutMapping(
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = MediaType.APPLICATION_JSON_VALUE, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public PersonDTO update(
            @RequestBody PersonDTO person // @RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.update(person);
    }

    @DeleteMapping(
            value = "/{id}",
            //produces -> dados ENVIADOS DA a API (response)
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(
            @PathVariable String id
    ) {
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build(); // irá retornar um response de sucesso com status 204 (No Content)
    }
}
