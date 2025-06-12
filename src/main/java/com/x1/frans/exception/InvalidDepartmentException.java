package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidDepartmentException extends BaseCustomException {

    public InvalidDepartmentException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_DEPARTMENT;
    }
}