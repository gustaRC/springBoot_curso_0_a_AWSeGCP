package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import tools.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"address", "lastName", "firstName", "id"}) // ordenar DTO de maneira especifica
@JsonFilter("PersonFilter") // mesmo nome atribuido no index em ObjectMapperConfig
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("codigo") // renomear campo e outras funcionalidades
    private Long id;

    @JsonProperty(value = "first_name") // renomear campo e outras funcionalidade
    private String firstName;

    //@JsonIgnore() // ignorar campo, ou seja, não o exibir
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL) // somente será exibido se o valor NÂO FOR NULO
    private String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) // somente será exibido se o valor NÂO ESTIVER VAZIO
    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy") // formatar campo data
    private Date birthDay; // new Date() = "2026-07-24T17:58:38.901Z"

    @JsonSerialize(using = GenderSerializer.class) // tratativa para ser exibido "M" ou "F" de acordo com genêro
    private String gender;

    private String sensitiveData; // mesmo nome atribuido no ObjectMapperConfig

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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getBirthDay(), personDTO.getBirthDay()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getSensitiveData(), personDTO.getSensitiveData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber(), getAddress(), getBirthDay(), getGender(), getSensitiveData());
    }
}
