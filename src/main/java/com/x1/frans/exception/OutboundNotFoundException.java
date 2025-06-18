package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class OutboundNotFoundException extends BaseCustomException {

    public OutboundNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.OUTBOUND_NOT_FOUND;
    }
}
