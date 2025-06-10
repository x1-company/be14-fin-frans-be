package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidTimeFormatException extends BaseCustomException {
    public InvalidTimeFormatException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_TIME_FORMAT;
    }
}
