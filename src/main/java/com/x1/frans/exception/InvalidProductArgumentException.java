package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidProductArgumentException extends BaseCustomException {
    public InvalidProductArgumentException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_PRODUCT_ARGUMENT;
    }
}
