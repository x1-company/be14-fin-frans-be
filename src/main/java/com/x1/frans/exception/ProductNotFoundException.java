package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ProductNotFoundException extends BaseCustomException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PRODUCT_NOT_FOUND;
    }

}
