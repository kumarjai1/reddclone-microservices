package com.example.usermicroservice.exception;

public class EntityAlreadyExists extends Exception{
    public EntityAlreadyExists(String msg) {
        super(msg);
    }
}
