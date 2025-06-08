package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ExpiredRefreshTokenException extends BaseCustomException {

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.EXPIRED_REFRESH_TOKEN;
    }
}
