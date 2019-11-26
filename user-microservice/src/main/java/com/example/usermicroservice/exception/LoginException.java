package com.example.usermicroservice.exception;

public class LoginException extends RuntimeException {

    public LoginException(String msg) {
        super(msg);
    }
}
