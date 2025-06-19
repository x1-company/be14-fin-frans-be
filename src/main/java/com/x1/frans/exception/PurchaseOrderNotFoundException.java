package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class PurchaseOrderNotFoundException extends BaseCustomException {
    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PURCHASE_ORDER_NOT_FOUND;
    }
}
