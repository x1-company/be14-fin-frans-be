package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidUserTypeException extends BaseCustomException {

    public InvalidUserTypeException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_USER_TYPE;
    }
}