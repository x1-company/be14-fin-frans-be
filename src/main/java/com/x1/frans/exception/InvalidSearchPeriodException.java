package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidSearchPeriodException extends BaseCustomException{
    public InvalidSearchPeriodException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_SEARCH_PERIOD;
    }
}
