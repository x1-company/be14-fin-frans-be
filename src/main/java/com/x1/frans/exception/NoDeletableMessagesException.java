package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class NoDeletableMessagesException extends BaseCustomException {
    public NoDeletableMessagesException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
      return ErrorCode.NO_DELETABLE_MESSAGES;
    }

}
