package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers.BookController;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers.PersonController;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.BookDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.RequiredObjectIsNullException;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.ResourceNotFoundException;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper.ObjectMapper;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Book;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public List<BookDTO> findAll() {
        logger.info("Finding all books!");

//      List<Book> -> List<BookDTO>
        List<BookDTO> list = ObjectMapper.parseListObjects(repository.findAll(), BookDTO.class);
        list.forEach(BookService::addHateoasLinks);
        return list;
    }

    public BookDTO findById(Integer id) {
        logger.info("Finding a specific book! ID: " + id);
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Book found for this ID!"));

        BookDTO dto = ObjectMapper.parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO create(BookDTO dto) {
        if (dto == null) throw new RequiredObjectIsNullException();

        logger.info("Creating a new book!");

        Book entity = ObjectMapper.parseObject(dto, Book.class);

        BookDTO newDto = ObjectMapper.parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(newDto);
        return newDto;
    }

    public BookDTO update(BookDTO dto) {
        if (dto == null) throw new RequiredObjectIsNullException();

        logger.info("Updating a specific book!");

        Book existingEntity = ObjectMapper.parseObject(findById(dto.getId()), Book.class);

        existingEntity.setTitle(dto.getTitle());
        existingEntity.setAuthor(dto.getAuthor());
        existingEntity.setPrice(dto.getPrice());
        existingEntity.setLaunch_date(dto.getLaunch_date());

        BookDTO newDto = ObjectMapper.parseObject(
                repository.save(existingEntity),
                BookDTO.class
        );
        addHateoasLinks(newDto);
        return newDto;
    }

    public void delete(Integer id) {
        logger.info("Deleting a specific book!");

        BookDTO dto = findById(id);

        repository.deleteById(dto.getId());
    }

    private static void addHateoasLinks(BookDTO dto) {
        dto.add(
            linkTo(
                methodOn(BookController.class).findById(dto.getId())
            ).withSelfRel().withType("GET")
        );
        dto.add(
            linkTo(
                methodOn(BookController.class).findAll()
            ).withRel("findAll").withType("GET")
        );
        dto.add(
                linkTo(
                    methodOn(BookController.class).delete(dto.getId())
                ).withRel("delete").withType("DELETE")
        );
        dto.add(
            linkTo(
                methodOn(BookController.class).create(dto)
            ).withRel("create").withType("POST")
        );
        dto.add(
            linkTo(
                methodOn(BookController.class).update(dto)
            ).withRel("update").withType("PUT")
        );
    }

}
