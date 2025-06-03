package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public abstract class BaseCustomException extends RuntimeException{
    public abstract ErrorCode getErrorCode();

    public BaseCustomException(String message) {
        super(message);
    }
}
