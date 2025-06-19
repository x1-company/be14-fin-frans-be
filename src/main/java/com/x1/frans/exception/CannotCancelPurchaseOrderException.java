package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class CannotCancelPurchaseOrderException extends BaseCustomException {
    public CannotCancelPurchaseOrderException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
      return ErrorCode.CANNOT_CANCEL_PURCHASE_ORDER;
    }
}
