package com.binarios.gestionticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class NoAuthorithyException extends RuntimeException{
    public NoAuthorithyException(String message){
        super(message);
    }
}
