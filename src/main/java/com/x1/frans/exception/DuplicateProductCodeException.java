package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class DuplicateProductCodeException extends BaseCustomException {
    public DuplicateProductCodeException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_PRODUCT_CODE;
    }
}
