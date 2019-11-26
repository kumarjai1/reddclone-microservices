package com.example.postmicroservice.exception;

public class EntityAlreadyExists extends Exception {

    public EntityAlreadyExists (String msg) {
        super(msg);
    }
}
