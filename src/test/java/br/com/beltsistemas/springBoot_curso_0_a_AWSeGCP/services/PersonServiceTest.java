package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.PersonDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.RequiredObjectIsNullException;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository.PersonRepository;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ciclo de Vida: Objetos/Métodos criados somente serão para essa classe, caso haja outra instância da mesma classe não usará nada daqui
@ExtendWith(MockitoExtension.class) // "Conecta" uma extensão externa ao ciclo de vida do teste.
// Ao passar MockitoExtension.class, você está dizendo: "antes de cada teste, deixa o Mockito inicializar os mocks que eu declarar".
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository; // NÃO ESTÁ IMPORTANDO O PersonRepository DE FATO!
//  Os testes não possuem acesso ao banco real. Com isso, o Mockito CRIA um "dublê", ou seja, cria uma COPÍA SIMULADA

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        //MockitoAnnotations.openMocks(this); // EM VERSÕES ANTIGAS: se não adicionarmos este comando as Annotations @Mock/InjectMocks não funcionarão
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1); // biblioteca INSTANCE para criação de Mock Automatizado (Testar depois)
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person)); // prepara o ambiente de teste, criando um cenário simulado sem depender do banco real (pois os testes/mock não possuem acesso ao banco real)
//      when(objetoMock.metodo(parâmetros_casoPrecise)).thenReturn(valorDesejado_podendoSerUmErroOuThrowError);

        PersonDTO result = service.findById(1L);

        assertNotNull(result); // TRADUÇÃO: "afirmar que não é nulo" | 'result' existe? Ou seja, 'result' não é nulo?
        assertNotNull(result.getId()); // ID de 'result' existe? Ou seja, ID de 'result' não é nulo?
        assertEquals(person.getId(), result.getId()); // TRADUÇÃO: "afirmar igual" | ID de 'person' é igual ao ID de 'result'?
        assertNotNull(result.getLinks()); // Links HATEOAS de 'result' existe? Ou seja, Links HATEOAS de 'result' não é nulo?
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());

        assertTrue(result.getLinks().stream() // verificando Link 'self' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("self") &&
                        link.getHref().endsWith("/api/person/v1/1") &&
                        link.getType().equals("GET")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'delete' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("delete") &&
                        link.getHref().endsWith("/api/person/v1/1") &&
                        link.getType().equals("DELETE")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'findAll' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("findAll") &&
                        link.getHref().endsWith("/api/person/v1") &&
                        link.getType().equals("GET")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'create' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("create") &&
                        link.getHref().endsWith("/api/person/v1") &&
                        link.getType().equals("POST")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'update' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("update") &&
                        link.getHref().endsWith("/api/person/v1") &&
                        link.getType().equals("PUT")
                )
        );
    }

    @Test
    void create() {
        Person person = input.mockEntity(1); // biblioteca INSTANCE para criação de Mock Automatizado (Testar depois)
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted); // prepara o ambiente de teste, criando um cenário simulado sem depender do banco real (pois os testes/mock não possuem acesso ao banco real)
//      when(objetoMock.metodo(parâmetros_casoPrecise)).thenReturn(valorDesejado_podendoSerUmErroOuThrowError);

        PersonDTO result = service.create(dto);

        assertNotNull(result); // TRADUÇÃO: "afirmar que não é nulo" | 'result' existe? Ou seja, 'result' não é nulo?
        assertNotNull(result.getId()); // ID de 'result' existe? Ou seja, ID de 'result' não é nulo?
        assertEquals(person.getId(), result.getId()); // TRADUÇÃO: "afirmar igual" | ID de 'person' é igual ao ID de 'result'?
        assertNotNull(result.getLinks()); // Links HATEOAS de 'result' existe? Ou seja, Links HATEOAS de 'result' não é nulo?
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());

        assertTrue(result.getLinks().stream() // verificando Link 'self' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'delete' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'findAll' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'create' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'update' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );
    }

    @Test
    void testCreateWithNullException() {
        Exception exception = assertThrows( // TRADUÇÃO: "afirma lances (lançamento de exceção)" | assertThrows() = método que verifica se uma exceção foi lançada
            RequiredObjectIsNullException.class, () -> {
                service.create(null);
            });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage)); // TRADUÇÃO: "afirmar verdadeiro" | assertTrue() = método que verifica se a condição é verdadeira
    }

    @Test
    void update() {
        Person person = input.mockEntity(1); // biblioteca INSTANCE para criação de Mock Automatizado (Testar depois)
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

//      verificando se existe algum "registro" com o id 1 (comportamento presente no PersonService.update)
        when(repository.findById(1L)).thenReturn(Optional.of(person));
//      salvando um novo "registro"
        when(repository.save(person)).thenReturn(persisted); // prepara o ambiente de teste, criando um cenário simulado sem depender do banco real (pois os testes/mock não possuem acesso ao banco real)
//      when(objetoMock.metodo(parâmetros_casoPrecise)).thenReturn(valorDesejado_podendoSerUmErroOuThrowError);

        PersonDTO result = service.update(dto);

        assertNotNull(result); // TRADUÇÃO: "afirmar que não é nulo" | 'result' existe? Ou seja, 'result' não é nulo?
        assertNotNull(result.getId()); // ID de 'result' existe? Ou seja, ID de 'result' não é nulo?
        assertEquals(person.getId(), result.getId()); // TRADUÇÃO: "afirmar igual" | ID de 'person' é igual ao ID de 'result'?
        assertNotNull(result.getLinks()); // Links HATEOAS de 'result' existe? Ou seja, Links HATEOAS de 'result' não é nulo?
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());

        assertTrue(result.getLinks().stream() // verificando Link 'self' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'delete' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'findAll' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'create' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );
        assertTrue(result.getLinks().stream() // verificando Link 'update' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );
    }

    @Test
    void testUpdateWithNullException() {
        Exception exception = assertThrows( // TRADUÇÃO: "afirma lances (lançamento de exceção)" | assertThrows() = método que verifica se uma exceção foi lançada
                RequiredObjectIsNullException.class, () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage)); // TRADUÇÃO: "afirmar verdadeiro" | assertTrue() = método que verifica se a condição é verdadeira
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1); // biblioteca INSTANCE para criação de Mock Automatizado (Testar depois)
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person)); // prepara o ambiente de teste, criando um cenário simulado sem depender do banco real (pois os testes/mock não possuem acesso ao banco real)
//      when(objetoMock.metodo(parâmetros_casoPrecise)).thenReturn(valorDesejado_podendoSerUmErroOuThrowError);

        service.delete(1L); // método delete não retorna nada, portanto não há como verificar o retorno, contudo podemos verificar se os métodos dentro do service foram chamados
        verify(repository, times(1)).findById(anyLong() /*Mockito - qualquer long*/); // verifica se o método findById foi chamado 1 vez
        verify(repository, times(1)).deleteById(anyLong() /*Mockito - qualquer objeto do tipo Person*/); // verifica se o método delete foi chamado 1 vez
        verifyNoMoreInteractions(repository); // verifica se não houve mais interações/ativações
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList(); // biblioteca INSTANCE para criação de Mock Automatizado (Testar depois)

        when(repository.findAll()).thenReturn(list); // prepara o ambiente de teste, criando um cenário simulado sem depender do banco real (pois os testes/mock não possuem acesso ao banco real)

        List<PersonDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        PersonDTO personOne =  people.get(0);

        assertNotNull(personOne); // TRADUÇÃO: "afirmar que não é nulo" | 'personOne' existe? Ou seja, 'personOne' não é nulo?
        assertNotNull(personOne.getId()); // ID de 'personOne' existe? Ou seja, ID de 'personOne' não é nulo?
        assertEquals(people.get(0).getId(), personOne.getId()); // TRADUÇÃO: "afirmar igual" | ID de 'person' é igual ao ID de 'personOne'?
        assertNotNull(personOne.getLinks()); // Links HATEOAS de 'personOne' existe? Ou seja, Links HATEOAS de 'personOne' não é nulo?
        assertEquals(people.get(0).getAddress(), personOne.getAddress());
        assertEquals(people.get(0).getFirstName(), personOne.getFirstName());
        assertEquals(people.get(0).getLastName(), personOne.getLastName());

        assertTrue(personOne.getLinks().stream() // verificando Link 'self' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(personOne.getLinks().stream() // verificando Link 'delete' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );
        assertTrue(personOne.getLinks().stream() // verificando Link 'findAll' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(personOne.getLinks().stream() // verificando Link 'create' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );
        assertTrue(personOne.getLinks().stream() // verificando Link 'update' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );

        PersonDTO personTwo =  people.get(4);

        assertNotNull(personTwo); // TRADUÇÃO: "afirmar que não é nulo" | 'personTwo' existe? Ou seja, 'personTwo' não é nulo?
        assertNotNull(personTwo.getId()); // ID de 'personTwo' existe? Ou seja, ID de 'personTwo' não é nulo?
        assertEquals(people.get(4).getId(), personTwo.getId()); // TRADUÇÃO: "afirmar igual" | ID de 'person' é igual ao ID de 'personTwo'?
        assertNotNull(personTwo.getLinks()); // Links HATEOAS de 'personTwo' existe? Ou seja, Links HATEOAS de 'personTwo' não é nulo?
        assertEquals(people.get(4).getAddress(), personTwo.getAddress());
        assertEquals(people.get(4).getFirstName(), personTwo.getFirstName());
        assertEquals(people.get(4).getLastName(), personTwo.getLastName());

        assertTrue(personTwo.getLinks().stream() // verificando Link 'self' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(personTwo.getLinks().stream() // verificando Link 'delete' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );
        assertTrue(personTwo.getLinks().stream() // verificando Link 'findAll' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );
        assertTrue(personTwo.getLinks().stream() // verificando Link 'create' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );
        assertTrue(personTwo.getLinks().stream() // verificando Link 'update' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );
    }

    /*
    POSSÍVEL CRIAR MÉTODOS DE REUTILIZAÇÃO, exemplo Claude
        @Test
        void findAll() {
            List<Person> list = input.mockEntityList();

            when(repository.findAll()).thenReturn(list);

            List<PersonDTO> people = service.findAll();

            assertNotNull(people);
            assertEquals(14, people.size());

            assertPersonDTO(people, 0);
            assertPersonDTO(people, 4);
        }

        private void assertPersonDTO(List<PersonDTO> people, int index) {
            PersonDTO expected = people.get(index);
            PersonDTO actual = people.get(index); // mantido caso queira comparar contra outra fonte futuramente

            assertNotNull(actual);
            assertNotNull(actual.getId());
            assertEquals(expected.getId(), actual.getId());
            assertNotNull(actual.getLinks());
            assertEquals(expected.getAddress(), actual.getAddress());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());

            assertHateoasLinks(actual);
        }

        private void assertHateoasLinks(PersonDTO personDTO) {
            assertLinkPresent(personDTO, "self", "/api/person/v1/1", "GET");
            assertLinkPresent(personDTO, "delete", "/api/person/v1/1", "DELETE");
            assertLinkPresent(personDTO, "findAll", "/api/person/v1", "GET");
            assertLinkPresent(personDTO, "create", "/api/person/v1", "POST");
            assertLinkPresent(personDTO, "update", "/api/person/v1", "PUT");
        }

        private void assertLinkPresent(PersonDTO personDTO, String rel, String hrefSuffix, String type) {
            boolean found = personDTO.getLinks().stream()
                    .anyMatch(link ->
                            link.getRel().value().equals(rel) &&
                            link.getHref().endsWith(hrefSuffix) &&
                            link.getType().equals(type)
                    );
            assertTrue(found, () -> "Link esperado não encontrado: rel=" + rel + ", type=" + type);
        }
    */
}