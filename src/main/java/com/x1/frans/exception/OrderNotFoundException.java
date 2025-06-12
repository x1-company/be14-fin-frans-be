package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class OrderNotFoundException extends BaseCustomException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_NOT_FOUND;
    }
}
