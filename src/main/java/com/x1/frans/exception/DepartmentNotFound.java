package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class DepartmentNotFound extends BaseCustomException {

    public DepartmentNotFound(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.DEPARTMENT_NOT_FOUND; }
}
