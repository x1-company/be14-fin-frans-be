package com.x1.frans.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountDeletedException extends AuthenticationException {
    public AccountDeletedException(String message) {
        super(message);
    }
}
