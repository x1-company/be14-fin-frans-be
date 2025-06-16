package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ApprovalActionFailedException extends BaseCustomException{
    public ApprovalActionFailedException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.APPROVAL_ACTION_FAILED;
    }
}
