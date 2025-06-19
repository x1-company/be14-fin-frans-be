package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class NotFoundStatisticsException extends BaseCustomException {
    public NotFoundStatisticsException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND_STATISTICS;
    }
}
