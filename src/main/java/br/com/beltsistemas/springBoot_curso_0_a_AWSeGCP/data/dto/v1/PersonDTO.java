package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"address", "lastName", "firstName", "id"}) // ordenar DTO de maneira especifica
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("codigo") // renomear campo e outras funcionalidades
    private Long id;

    @JsonProperty(value = "first_name") // renomear campo e outras funcionalidade
    private String firstName;

    @JsonIgnore() // ignorar campo, ou seja, não o exibir
    private String lastName;

    private String address;

    public PersonDTO() {
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
        if (!(o instanceof PersonDTO person)) return false;
        return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress());
    }
}
