package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class CannotDeleteOrderException extends BaseCustomException {
    public CannotDeleteOrderException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.CANNOT_DELETE_ORDER;
    }
}
