package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers.withjson;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config.TestConfigs;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.integrationstests.dto.PersonDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.integrationstests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// iremos seguir um fluxo específico de testes: create -> findById -> update -> delete, por isso a anotação @TestMethodOrder
// para armazenar o estado de uma variável entre os testes
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification requestSpecification; // lib REST-assured para fazer requisições HTTP e validar respostas
    private static ObjectMapper objectMapper; // lib Jackson para serializar e desserializar objetos Java em JSON e vice-versa

    private static PersonDTO person;

    @BeforeEach
    void setUp() { // setUp: método que será executado antes de cada teste, para inicializar as variáveis requestSpecification e objectMapper
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // para ignorar propriedades desconhecidas no JSON e não lançar exceção
        // como estamos usando o HATEOAS, deveremos ignorar as propriedades _links e page, que são adicionadas automaticamente pelo Spring Data REST

        // porque inicializar fora do método setUp? Porque o requestSpecification é estático, e se inicializarmos dentro do setUp, ele será reinicializado a cada teste, perdendo o estado da variável entre os testes
        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_PROMPTUSCD)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // para logar todas as informações da requisição/request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // para logar todas as informações da resposta/response
            .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200) //erro 500: Column 'first_name' cannot be null -> está sendo configurado como firstName variavel, contudo está sendo descrito dentro da Entidade Person a configuração para compatibilidade
                .extract()
                    .body()
                        .asString();
        // o método asString() converte o body da resposta em String, para que possamos desserializar em um objeto Java
        // sem isso poderíamos usar o método as(PersonDTO.class), mas como estamos usando o HATEOAS, o body da resposta contém propriedades desconhecidas (_links e page), que não estão mapeadas na classe PersonDTO, e isso causaria uma exceção de desserialização

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class); // desserializando o JSON em um objeto Java
        person = createdPerson; // atualizando a variável person com o objeto criado, para que possamos usar nos próximos testes

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertEquals("Biricubicu", createdPerson.getFirstName());
        assertEquals("Siricuticu", createdPerson.getLastName());
        assertEquals("Casa do Chapéu, n69", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    private void mockPerson() {
        person.setFirstName("Biricubicu");
        person.setLastName("Siricuticu");
        person.setAddress("Casa do Chapéu, n69");
        person.setGender("Male");
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}