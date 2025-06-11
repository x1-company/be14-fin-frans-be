package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class UnauthorizedAccessException extends BaseCustomException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.UNAUTHORIZED_ACCESS;
    }
}
