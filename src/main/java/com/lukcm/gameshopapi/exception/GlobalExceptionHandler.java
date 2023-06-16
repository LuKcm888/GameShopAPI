package com.lukcm.gameshopapi.exception;

import org.springframework.http.HttpHeaders;
import com.mongodb.MongoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

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

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

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
        String methodName = ".handleMongoException";
        logger.error("{}: Error connecting to MongoDB.", methodName);

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
        String methodName = ".handleMissingParams";
        String paramName = ex.getParameterName();

        logger.error("{}: The required parameter {} is missing.  {} ", methodName, paramName, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("The required parameter " + paramName + " is missing.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AccessDeniedException across the whole application.
     *
     * @param ex      the exception caught
     * @param request the current web request
     * @return a ResponseEntity object with an error message, empty headers, and an HTTP status code
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Access denied message here", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}