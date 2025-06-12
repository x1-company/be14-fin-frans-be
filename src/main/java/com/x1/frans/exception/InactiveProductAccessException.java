package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InactiveProductAccessException extends BaseCustomException {
    public InactiveProductAccessException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INACTIVE_PRODUCT_ACCESS;
    }
}
