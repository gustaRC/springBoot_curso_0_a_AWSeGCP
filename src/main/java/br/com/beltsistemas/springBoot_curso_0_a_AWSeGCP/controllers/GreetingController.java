package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!"; // template da mensagem de exibição
    private final AtomicLong counter = new AtomicLong(); // geração de um número Long

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        // NÃO é obrigatório passar a configuração do value, mas assim espera-se que o nome do parâmetro da função seja o mesmo do requestParam
        return  new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
