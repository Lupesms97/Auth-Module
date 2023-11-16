package com.developement.crm.exceptionHandlers;

public class NoUserFindOnSession extends RuntimeException{

    public NoUserFindOnSession(String mensagem) {
        super(mensagem);
    }
}
