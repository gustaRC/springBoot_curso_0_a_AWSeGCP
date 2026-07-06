package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //status que será retornado no response (400, 500, 201) -> (não necessariamente será retorno de erro)
public class UnsupportedMathOperationException extends RuntimeException {

    //método já criado por padrão ao criar a classe tipo exception
    public UnsupportedMathOperationException(String message) {
        super(message);
    }
}
