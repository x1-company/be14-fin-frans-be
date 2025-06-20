package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class CannotModifyDeliveryInfoException extends BaseCustomException {
    public CannotModifyDeliveryInfoException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.CANNOT_MODIFY_DELIVERY_INFO;
    }
}
