package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidOrderStatusException extends BaseCustomException {
    public InvalidOrderStatusException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_ORDER_STATUS;
    }
}
