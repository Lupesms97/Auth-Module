package com.developement.crm.exceptionHandlers;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);

    }
}
