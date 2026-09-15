package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.repository;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    //Query não obrigatório, JPA já o disponibilizará para nós com uso da convensão: "findBy[ATRIBUTO]"
    @Query("SELECT u FROM User u WHERE u.userName =: userName")
    User findByUsername(@Param("userName") String userName);
}
