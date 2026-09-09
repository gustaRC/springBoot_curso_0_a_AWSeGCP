package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

public interface TestConfigs {

    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_PROMPTUSCD = "https://www.promptuscd.com.br"; // já acrescentada para o CORS no backend
    String ORIGIN_ERRADA = "https://www.errada.com.br"; // não acrescentada para o CORS no backend
}
