package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class UnreadMessagesCannotBeDeletedException extends BaseCustomException {
    public UnreadMessagesCannotBeDeletedException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.UNREAD_MESSAGES_CANNOT_BE_DELETED;
    }

}
