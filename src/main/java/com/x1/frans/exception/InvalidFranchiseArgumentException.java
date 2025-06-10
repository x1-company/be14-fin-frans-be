package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidFranchiseArgumentException extends BaseCustomException {
    public InvalidFranchiseArgumentException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_FRANCHISE_ARGUMENT;
    }
}
