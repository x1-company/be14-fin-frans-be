package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class OrderRejectReasonRequiredException extends BaseCustomException {

    public OrderRejectReasonRequiredException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_REJECT_REASON_REQUIRED;
    }
}
