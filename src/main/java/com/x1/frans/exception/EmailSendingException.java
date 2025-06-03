package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class EmailSendingException extends BaseCustomException {

    public EmailSendingException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.EMAIL_SEND_FAILED;
    }
}