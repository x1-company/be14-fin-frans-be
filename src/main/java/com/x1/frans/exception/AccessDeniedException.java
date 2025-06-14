package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class AccessDeniedException extends BaseCustomException {

    public AccessDeniedException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ACCESS_DENIED;
    }
}
