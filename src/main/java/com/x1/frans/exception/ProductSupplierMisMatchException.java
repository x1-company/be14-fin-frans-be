package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ProductSupplierMisMatchException extends BaseCustomException {
    public ProductSupplierMisMatchException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PRODUCT_SUPPLIER_MISMATCH;
    }
}
