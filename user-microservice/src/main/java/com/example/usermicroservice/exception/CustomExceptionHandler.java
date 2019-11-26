package com.example.usermicroservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ExceptionRes> handleUserNotExistException(UserNotExistException ex){
        List<String> messages = Arrays.asList("User does not exist. Sign up before logging in!");
        ExceptionRes exceptionRes = new ExceptionRes(HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(exceptionRes, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err->err.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionRes exceptionRes = new ExceptionRes(HttpStatus.BAD_REQUEST, errorMessages);

        return new ResponseEntity<>(exceptionRes, HttpStatus.BAD_REQUEST);
    }
}
