package com.binarios.gestionticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnexpectedProblemException extends RuntimeException{
    public UnexpectedProblemException(String message){
        super(message);
    }
}