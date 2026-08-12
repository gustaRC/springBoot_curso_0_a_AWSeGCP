package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.PersonDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v2.PersonDTOV2;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "Person Endpoint", description = "Endpoints for Managing Person API")
public class PersonController {

    @Autowired
    private PersonService service; // == private PersonService service = new PersonService();

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE } /* produces -> dados ENVIADOS DA a API (response) */ )
    @Operation(
            summary = "Finds all people",
            description = "Finds all people",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))) }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE } /* produces -> dados ENVIADOS DA a API (response) */
    )
    @Operation(
            summary = "Finds a person by ID",
            description = "Finds a person by ID",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
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
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public PersonDTO create(
            @RequestBody PersonDTO person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.create(person);
    }

    @PostMapping(
            value = "/v2",
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public PersonDTOV2 createV2(
            @RequestBody PersonDTOV2 person //@RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.createV2(person);
    }

    @PutMapping(
            //consumes -> dados ENVIADOS PARA a API (request)
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }, // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
            //produces -> dados ENVIADOS DA a API (response)
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }  // opcional o consumes/produces, contudo caso não está descrito o Swagger se perderá na documentação
    )
    public PersonDTO update(
            @RequestBody PersonDTO person // @RequestBody para indicar a necessidade de preenchimento no Body da aplicação
    ) {
        return service.update(person);
    }

    @DeleteMapping(
            value = "/{id}",
            //produces -> dados ENVIADOS DA a API (response)
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<?> delete(
            @PathVariable String id
    ) {
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build(); // irá retornar um response de sucesso com status 204 (No Content)
    }
}
