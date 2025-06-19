package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidPurchaseRequestProductException extends BaseCustomException {
    public InvalidPurchaseRequestProductException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_PURCHASE_REQUEST_PRODUCT;
    }
}
