package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidOrderRejectConditionException extends BaseCustomException {

    public InvalidOrderRejectConditionException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_ORDER_REJECT_CONDITION;
    }
}
