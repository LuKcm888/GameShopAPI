package com.lukcm.gameshopapi.exception;

import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<String> handleMongoException(MongoException e) {
        // log the error, here I'm just printing the stack trace, you might want to use a logger
        e.printStackTrace();

        // return an error response
        return new ResponseEntity<>("Error connecting to MongoDB: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}