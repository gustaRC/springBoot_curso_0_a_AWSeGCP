package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.controllers;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math") //rota padrão do MathController (todo endpoint contido aqui terá previamente o: '/math/...')
public class MathController {

    @Autowired
    private MathService mathService;

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne, // não é obrigatório a configuração do value, mas assim espera-se que o PathVariable tenha o mesmo nome do parâmetro da função
            @PathVariable String numberTwo
    ) {
        return mathService.sum(numberOne, numberTwo);
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        return mathService.subtraction(numberOne, numberTwo);
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        return mathService.multiplication(numberOne, numberTwo);
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        return mathService.division(numberOne, numberTwo);
    }

    @RequestMapping("/average/{numberOne}/{numberTwo}")
    public Double average(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        return mathService.average(numberOne, numberTwo);
    }

    @RequestMapping("/square-root/{numberOne}")
    public Double squareRoot(
            @PathVariable String numberOne
    ) {
        return mathService.squareRoot(numberOne);
    }
}
