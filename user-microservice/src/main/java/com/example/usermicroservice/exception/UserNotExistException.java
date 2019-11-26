package com.example.usermicroservice.exception;

public class UserNotExistException extends Exception{
    public UserNotExistException(String message) {
        super(message);
    }
}
