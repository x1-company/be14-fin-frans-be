package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class UserNotFoundException extends BaseCustomException {

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_NOT_FOUND;
    }
}
