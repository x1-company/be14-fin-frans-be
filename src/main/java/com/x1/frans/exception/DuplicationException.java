package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class DuplicationException extends BaseCustomException {

    public DuplicationException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_RESOURCE;
    }
}