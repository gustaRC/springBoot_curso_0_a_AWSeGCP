package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
