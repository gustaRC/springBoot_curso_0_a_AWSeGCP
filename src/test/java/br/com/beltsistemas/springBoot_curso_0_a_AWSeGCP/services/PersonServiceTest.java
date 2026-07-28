package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ciclo de Vida: Objetos/Métodos criados somente serão para essa classe, caso haja outra instância da mesma classe não usará nada daqui
@ExtendWith(MockitoExtension.class) // "Conecta" uma extensão externa ao ciclo de vida do teste.
// Ao passar MockitoExtension.class, você está dizendo: "antes de cada teste, deixa o Mockito inicializar os mocks que eu declarar".
class PersonServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void findById() {
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