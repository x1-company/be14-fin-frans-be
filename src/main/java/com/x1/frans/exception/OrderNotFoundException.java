package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class OrderNotFoundException extends BaseCustomException {
    @Override
    public ErrorCode getErrorCode() {
        return null;
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
