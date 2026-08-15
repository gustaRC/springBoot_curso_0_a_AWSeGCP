package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.BookDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Book;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository.BookRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ciclo de Vida: Objetos/Métodos criados somente serão para essa classe, caso haja outra instância da mesma classe não usará nada daqui
@ExtendWith(MockitoExtension.class) // "Conecta" uma extensão externa (no caso, o Mockito) ao ciclo de vida do teste.
class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    private final String ENDS_WITH_URI_DEFAULT = "/api/book/v1"; // para evitar repetição de código

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        Book entityMock = Instancio.create(Book.class); // biblioteca INSTANCE para criação de Mock Automatizado (Testar depois)

//      estamos simulando o processo de busca de um registro no banco de dados, retornando um objeto mockado (entityMock) quando o método findById for chamado com o ID do entityMock. Isso permite testar a lógica do serviço sem depender de um banco de dados real.
//      em resumo, estamos "testando" o findById do repository
        when(repository.findById(entityMock.getId())).thenReturn(Optional.of(entityMock));

        BookDTO resultDTO = service.findById(entityMock.getId());

        assertResourceData(resultDTO, entityMock);

        assertHateoasLinks(resultDTO);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void assertResourceData(BookDTO resultDto, Book entityMock) {
        assertNotNull(resultDto);
        assertNotNull(resultDto.getId());
        assertEquals(entityMock.getId(), resultDto.getId());
        assertEquals(entityMock.getAuthor(), resultDto.getAuthor());
        assertEquals(entityMock.getPrice(), resultDto.getPrice());
        assertEquals(entityMock.getLaunch_date(), resultDto.getLaunch_date());
        assertEquals(entityMock.getTitle(), resultDto.getTitle());

    }

    private String getEndsWithUriDefaultWithPathVariable(String pathVariable) {
        return ENDS_WITH_URI_DEFAULT + "/" + pathVariable;
    }

    private void assertHateoasLinks(BookDTO dto) {
        assertNotNull(dto.getLinks());

        assertLinkPresent(dto, "self", getEndsWithUriDefaultWithPathVariable(String.valueOf(dto.getId())), "GET");
        assertLinkPresent(dto, "delete", getEndsWithUriDefaultWithPathVariable(String.valueOf(dto.getId())), "DELETE");
        assertLinkPresent(dto, "findAll", ENDS_WITH_URI_DEFAULT, "GET");
        assertLinkPresent(dto, "create", ENDS_WITH_URI_DEFAULT, "POST");
        assertLinkPresent(dto, "update", ENDS_WITH_URI_DEFAULT, "PUT");
    }

    private void assertLinkPresent(BookDTO dto, String rel, String hrefSuffix, String type) {
        boolean found = dto.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals(rel) &&
                        link.getHref().endsWith(hrefSuffix) &&
                        link.getType().equals(type)
                );
        assertTrue(found, () -> "Link esperado não encontrado: rel=" + rel + ", type=" + type);
    }
}