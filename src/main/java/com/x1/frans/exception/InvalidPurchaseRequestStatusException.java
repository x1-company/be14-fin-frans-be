package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidPurchaseRequestStatusException extends BaseCustomException {
    public InvalidPurchaseRequestStatusException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
      return ErrorCode.INVALID_PURCHASE_REQUEST_STATUS;
    }
}
