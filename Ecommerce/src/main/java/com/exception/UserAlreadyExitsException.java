package com.exception;

public class UserAlreadyExitsException extends Exception{
    public UserAlreadyExitsException(String message) {
        super(message);
    }
}
