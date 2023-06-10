package com.lukcm.gameshopapi.exception;

import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Max_MacKoul
 *
 * GlobalExceptionHandler is a centralized exception handling class which catches and handles
 * exceptions thrown by different components (like services and controllers) in the application.
 * Using the @ControllerAdvice annotation makes this class applicable to all controllers in the application.
 *
 * This class currently handles two types of exceptions: MongoException and MissingServletRequestParameterException.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type MongoException. This type of exception is typically thrown
     * when there's an error connecting to MongoDB.
     *
     * @param e The MongoException that was thrown.
     * @return A ResponseEntity containing the error message and an HTTP status code indicating
     *         that an internal server error occurred.
     */
    @ExceptionHandler(MongoException.class)
    public ResponseEntity<String> handleMongoException(MongoException e) {
        // log the error, here I'm just printing the stack trace, you might want to use a logger
        e.printStackTrace();

        // return an error response
        return new ResponseEntity<>("Error connecting to MongoDB: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions of type MissingServletRequestParameterException. This type of exception
     * is thrown when a required request parameter is missing.
     *
     * @param ex The MissingServletRequestParameterException that was thrown.
     * @return A ResponseEntity containing the error message and an HTTP status code indicating
     *         that a bad request occurred.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        return new ResponseEntity<>("The required parameter " + paramName + " is missing.", HttpStatus.BAD_REQUEST);
    }

}