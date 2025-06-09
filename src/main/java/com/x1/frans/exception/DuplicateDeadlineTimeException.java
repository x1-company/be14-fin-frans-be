package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class DuplicateDeadlineTimeException extends BaseCustomException {

    public DuplicateDeadlineTimeException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DUPLICATE_DEADLINE_TIME;
    }
}
