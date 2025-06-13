package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class StatisticsUnauthorizedAccessException extends BaseCustomException {
    public StatisticsUnauthorizedAccessException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.STATISTICS_UNAUTHORIZED_ACCESS;
    }
}
