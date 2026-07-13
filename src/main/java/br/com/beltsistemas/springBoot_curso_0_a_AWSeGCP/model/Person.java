package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "person") // declaramos que é uma Entidade/Tabela (Person/person)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // já é identificado automaticamente que o @Id é uma column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 80) /*
    - Coluna com nome "first_name";
    - Não pode ser nulo;
    - Tamanho de no máximo 80 caracteres.
 */ private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, length = 80) /*
    - Coluna com nome "address" (como não foi definido o name, o padrão será o nome do atributo);
    - Não pode ser nulo;
    - Tamanho de no máximo 80 caracteres.
 */ private String address;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress());
    }
}
