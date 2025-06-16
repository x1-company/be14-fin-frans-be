package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidRejectConditionException extends BaseCustomException {

    public InvalidRejectConditionException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_ORDER_REJECT_CONDITION;
    }
}
