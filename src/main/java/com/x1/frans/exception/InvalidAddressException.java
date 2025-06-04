package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidAddressException extends BaseCustomException {

    public InvalidAddressException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_ADDRESS;
    }
}
