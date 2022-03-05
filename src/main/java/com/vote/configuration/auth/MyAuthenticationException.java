package com.vote.configuration.auth;

import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String msg){
        super(msg);
    }
}
