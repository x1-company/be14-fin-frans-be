package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class WarehouseNotFoundException extends BaseCustomException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.WAREHOUSE_NOT_FOUND;
    }
}
