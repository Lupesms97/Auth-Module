package com.developement.crm.exceptionHandlers;

public class SecurityConfigException extends RuntimeException {

    public SecurityConfigException(String message, SecurityConfigException e) {
        super(message);
    }


}
