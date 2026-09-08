package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.integrationstests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.mysql.MySQLContainer;

import java.util.Map;
import java.util.stream.Stream;

// Necessário o ContextConfiguration para que o Spring Boot saiba que essa classe é responsável por inicializar o contexto do Spring Boot com as configurações do TestContainers.
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    /*
    Classe Initializer tem a função de:
        - Inicializar o container do TestContainers antes da execução dos testes;
        - Configurar as propriedades de conexão com o banco de dados no contexto do Spring Boot.

    Por que é static?
        - Para só ter um Container do TestContainers durante a execução dos testes, e não um container para cada teste. Então, o container é inicializado uma única vez para todos os testes.
            - Está foi uma abordagem escolhida pelo professor!
            - Se não fosse static, cada teste criaria um container, o que seria poderia causar problemas de desempenho e recursos.
            - Com isso, o professor prefere perder em integridade nos testes em troca de desempenho. */
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer mySqlContainer = new MySQLContainer("mysql:9.7.2");

        private static void startContainers() {
            Startables.deepStart(Stream.of(mySqlContainer)).join();
        /*   Por que precisamos do deepStart?
                - O deepStart é necessário para garantir que todos os containers sejam iniciados corretamente,
                  mesmo que haja dependências entre eles. Ele percorre a árvore de containers e inicia cada um na ordem correta.
             Por que usamos o join()?
                - O join() é usado para aguardar a conclusão do processo de inicialização dos containers antes de prosseguir com a execução dos testes.
                  Isso garante que o banco de dados esteja pronto. E podemos configurar a imagem com:
                    - mySqlContainer.withDatabaseName("testdb").withUsername("testuser").withPassword("testpass");
            */
        }

        private static Map<String, Object> createConnectionConfiguration() {
            return Map.of(
                "spring.datasource.url", mySqlContainer.getJdbcUrl(), // como o container está gerando a URL dinamicamente, precisamos pegar a URL do container em tempo de execução
                "spring.datasource.username", mySqlContainer.getUsername(),
                "spring.datasource.password", mySqlContainer.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            // buscando as variáveis de ambiente do Spring Boot (algumas configuradas no próprio application.yaml)
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testcontainers = new MapPropertySource("testcontainers", createConnectionConfiguration());
            // adicionando configuração das propriedades ao testcontainers
            environment.getPropertySources().addFirst(testcontainers);
        }
    }
}
