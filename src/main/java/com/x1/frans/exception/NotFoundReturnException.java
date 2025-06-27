package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class NotFoundReturnException extends BaseCustomException {
    public NotFoundReturnException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.RETURN_NOT_FOUND;
    }
}
