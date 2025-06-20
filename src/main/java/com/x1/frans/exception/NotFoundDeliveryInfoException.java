package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class NotFoundDeliveryInfoException extends BaseCustomException {
    public NotFoundDeliveryInfoException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND_DELIVERY_INFO;
    }
}
