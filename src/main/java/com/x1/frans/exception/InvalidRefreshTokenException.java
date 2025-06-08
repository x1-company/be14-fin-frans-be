package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidRefreshTokenException extends BaseCustomException {

    public InvalidRefreshTokenException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_TOKEN;
    }
}
