package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception;

import java.util.Date;

//DTO padrão para resposta de erro nas requisições(controllers)
public record ExceptionResponse(Date timestamp, String message, String details) {}
