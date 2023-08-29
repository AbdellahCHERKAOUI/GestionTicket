package com.binarios.gestionticket.exception;


import org.springframework.security.authentication.DisabledException;

public class AccountDisabledException extends DisabledException {
    public AccountDisabledException(String msg) {
        super(msg);
    }
}
