package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class CannotChangeToDraftExceptioin extends BaseCustomException {

    public CannotChangeToDraftExceptioin(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.CANNOT_CHANGE_TO_DRAFT;
    }
}
