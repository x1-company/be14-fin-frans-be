package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidRequestException extends BaseCustomException {

    public InvalidRequestException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_REQUEST;
    }
}
