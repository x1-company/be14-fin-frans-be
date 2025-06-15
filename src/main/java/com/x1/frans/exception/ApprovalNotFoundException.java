package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ApprovalNotFoundException extends BaseCustomException {


    public ApprovalNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.APPROVAL_NOT_FOUND;
    }
}
