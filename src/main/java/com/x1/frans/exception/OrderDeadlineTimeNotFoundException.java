package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class OrderDeadlineTimeNotFoundException extends BaseCustomException {

    public OrderDeadlineTimeNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_DEADLINE_TIME_NOT_FOUND;
    }
}
