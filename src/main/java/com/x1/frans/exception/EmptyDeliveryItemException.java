package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class EmptyDeliveryItemException extends BaseCustomException {
    public EmptyDeliveryItemException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
      return ErrorCode.EMPTY_DELIVERY_ITEM;
    }
}
