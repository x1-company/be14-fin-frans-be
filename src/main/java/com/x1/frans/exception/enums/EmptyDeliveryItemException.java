package com.x1.frans.exception.enums;

import com.x1.frans.exception.BaseCustomException;

public class EmptyDeliveryItemException extends BaseCustomException {
    public EmptyDeliveryItemException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
      return ErrorCode.EMPTY_DELIVERY_ITEM;
    }
}
