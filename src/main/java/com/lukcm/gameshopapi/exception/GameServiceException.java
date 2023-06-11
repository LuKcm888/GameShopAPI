package com.lukcm.gameshopapi.exception;

public class GameServiceException extends RuntimeException{

    public GameServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
