package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ApprovalLineTemplateNotFoundException extends BaseCustomException {
    public ApprovalLineTemplateNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.APPROVAL_LINE_TEMPLATE_NOT_FOUND;
    }
}
