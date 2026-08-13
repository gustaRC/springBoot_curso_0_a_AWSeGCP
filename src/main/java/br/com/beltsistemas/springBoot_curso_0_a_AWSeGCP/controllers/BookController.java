package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.BookDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.mapper.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/v1")
public class BookController {

    @GetMapping()
    public List<BookDTO> findAll() {
    }

    @GetMapping(value = "/{id}")
    public BookDTO findById(int id) {
    }

    @PostMapping()
    public BookDTO create(BookDTO dto) {
    }

    @PutMapping()
    public BookDTO update(BookDTO dto) {
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(int id) {
    }
}
