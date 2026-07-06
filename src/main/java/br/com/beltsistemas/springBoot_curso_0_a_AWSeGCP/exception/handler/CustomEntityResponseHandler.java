package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.handler;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.ExceptionResponse;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.UnsupportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// (@ControllerAdvice + @RestController) -> professor usou essa abordagem
@RestControllerAdvice // tratativa para os controllers
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    //tratativa padrão para todas as requisições (retorno status 500)
    @ExceptionHandler(Exception.class) // Trata todas as exceções que não foram tratadas de forma específica (ex: UnsupportedMathOperationException)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse( // criação do objeto que será retornado
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // retorno do objeto criado + status de retorno (status 500)
    }

    @ExceptionHandler(UnsupportedMathOperationException.class) // Trata a exceção UnsupportedMathOperationException (toda a vez que essa classe for chamada irá cair nesse handler)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse( // criação do objeto que será retornado
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // retorno do objeto criado + status de retorno (status 400)
    }
}
