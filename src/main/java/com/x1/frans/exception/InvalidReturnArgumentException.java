package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidReturnArgumentException extends BaseCustomException {
    public InvalidReturnArgumentException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_RETURN_ARGUMENT;
    }
}
