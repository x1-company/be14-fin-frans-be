package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class AllMessagesAlreadyReadException extends BaseCustomException {
    public AllMessagesAlreadyReadException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ALL_MESSAGES_ALREADY_READ;
    }

}
