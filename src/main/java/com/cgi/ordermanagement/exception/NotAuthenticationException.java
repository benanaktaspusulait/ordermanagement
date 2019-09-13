package com.cgi.ordermanagement.exception;

import org.springframework.security.core.AuthenticationException;

public class NotAuthenticationException extends AuthenticationException {

    public NotAuthenticationException(final String msg) {
        super(msg);
    }

}