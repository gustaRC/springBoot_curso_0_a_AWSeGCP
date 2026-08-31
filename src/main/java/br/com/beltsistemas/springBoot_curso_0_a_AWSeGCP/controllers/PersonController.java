package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers.docs.PersonControllerDocs;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.PersonDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v2.PersonDTOV2;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@CrossOrigin // dessa forma qualquer domínio poderá acessar a API == NÃO RECOMENDADO!
//@CrossOrigin(origins = "http://localhost:8080") // referenciando somente acesso ao endpoint adicionado
@RestController
@RequestMapping("api/person/v1")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonService service; // == private PersonService service = new PersonService();

    @CrossOrigin() // qualquer domínio poderá acessar a API == NÃO RECOMENDADO!
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE} /* produces -> dados ENVIADOS DA a API (response) */)
    @Override
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    //@CrossOrigin(origins = {"http://localhost:8080", "https://www.gustavo.com.br"}) // referenciando somente acesso ao endpoint adicionado
    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE} /* produces -> dados ENVIADOS DA a API (response) */
    )
    @Override
    public PersonDTO findById(
            @PathVariable String id //@PathVariable indica a necessidade de preenchimento do caminho da URL: '/person/{id}'
    ) {
        PersonDTO person = service.findById(Long.parseLong(id));
        person.setBirthDay(new Date());
        //person.setPhoneNumber("(99) 99999-9999"); // se não preencher esse dado não será exibido
        //person.setAddress(""); // se estiver vazio não será exibido
        person.setSensitiveData("password_1234");
        return person;
    }

    @PostMapping(
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    @Override
    public PersonDTO create(
            @RequestBody PersonDTO person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.create(person);
    }

    @PostMapping(
            value = "/v2",
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    @Override
    public PersonDTOV2 createV2(
            @RequestBody PersonDTOV2 person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.createV2(person);
    }

    @PutMapping(
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    @Override
    public PersonDTO update(
            @RequestBody PersonDTO person // @RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.update(person);
    }

    @DeleteMapping(
            value = "/{id}",
            //produces -> dados ENVIADOS DA a API (response)
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<?> delete(
            @PathVariable String id
    ) {
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build(); // irá retornar um response de sucesso com status 204 (No Content)
    }
}
