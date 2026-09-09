package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.integrationstests.swagger;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config.TestConfigs;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.integrationstests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

// puxando a porta do application.yaml > server.port = 8888 (Mesmo valor referenciado na interface TestConfigs)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void shouldDisplaySwaggerUIPage() {
		//usar o REST-assured para fazer uma requisição GET para a URL do Swagger UI e verificar se a página é exibida corretamente
		var content = given()
			.basePath("/swagger-ui/index.html")    // dada determinada URL
				.port(TestConfigs.SERVER_PORT)     // com a porta previamente definida
			.when() 							   // quando a requisição for feita
				.get() 							   // e a requisição for do tipo GET
			.then()								   // então é esperado que
				.statusCode(200) 				   // a resposta tenha o status code 200 (OK)
			.extract() 							   // nisso será extraído
				.body()							   // do body da resposta
					.asString(); 				   // como uma String

		assertTrue(content.contains("Swagger UI")); // e será verificado se o conteúdo da página contém a string "Swagger UI"
	}
}
