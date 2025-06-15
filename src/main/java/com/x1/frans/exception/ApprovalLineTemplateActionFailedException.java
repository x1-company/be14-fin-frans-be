package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ApprovalLineTemplateActionFailedException extends BaseCustomException {
    public ApprovalLineTemplateActionFailedException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.APPROVAL_LINE_TEMPLATE_ACTION_FAILED;
    }
}
