package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.PersonDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ciclo de Vida: Objetos/Métodos criados somente serão para essa classe, caso haja outra instância da mesma classe não usará nada daqui
@ExtendWith(MockitoExtension.class) // "Conecta" uma extensão externa ao ciclo de vida do teste.
// Ao passar MockitoExtension.class, você está dizendo: "antes de cada teste, deixa o Mockito inicializar os mocks que eu declarar".
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        //MockitoAnnotations.openMocks(this); // EM VERSÕES ANTIGAS: se não adicionarmos este comando as Annotations @Mock/InjectMocks não funcionarão
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        PersonDTO result = service.findById(1L);

        assertNotNull(result); // TRADUÇÃO: "afirmar que não é nulo" | 'result' existe? Ou seja, 'result' não é nulo?
        assertNotNull(result.getId()); // ID de 'result' existe? Ou seja, ID de 'result' não é nulo?
        assertEquals(person.getId(), result.getId()); // TRADUÇÃO: "afirmar igual" | ID de 'person' é igual ao ID de 'result'?
        assertNotNull(result.getLinks()); // Links HATEOAS de 'result' existe? Ou seja, Links HATEOAS de 'result' não é nulo?
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());

        assertNotNull(result.getLinks().stream() // verificando Link 'self' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("self") &&
                        link.getHref().endsWith("/api/person/v1/1") &&
                        link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream() // verificando Link 'delete' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("delete") &&
                        link.getHref().endsWith("/api/person/v1/1") &&
                        link.getType().equals("DELETE")
                )
        );
        assertNotNull(result.getLinks().stream() // verificando Link 'findAll' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("findAll") &&
                        link.getHref().endsWith("/api/person/v1") &&
                        link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream() // verificando Link 'create' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("create") &&
                        link.getHref().endsWith("/api/person/v1") &&
                        link.getType().equals("POST")
                )
        );
        assertNotNull(result.getLinks().stream() // verificando Link 'update' do HATEOAS
                .anyMatch(link -> // TRADUÇÃO: "qualquer correspondência". Retorna se algum elemento deste fluxo satisfaz o predicado fornecido.
                        link.getRel().value().equals("update") &&
                        link.getHref().endsWith("/api/person/v1") &&
                        link.getType().equals("PUT")
                )
        );
    }

    @Test
    void create() {
    }

    @Test
    void createV2() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}