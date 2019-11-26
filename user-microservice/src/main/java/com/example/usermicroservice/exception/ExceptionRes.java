package com.example.usermicroservice.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ExceptionRes {
    private HttpStatus httpStatus;
    private List<String> message;


    public ExceptionRes(HttpStatus httpStatus, List<String> message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
