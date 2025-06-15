package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class PurchaseRequestNotFoundException extends BaseCustomException {

    public PurchaseRequestNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PURCHASE_REQUEST_NOT_FOUND;
    }

}