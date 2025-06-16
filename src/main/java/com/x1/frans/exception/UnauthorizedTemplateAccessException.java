package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class UnauthorizedTemplateAccessException extends BaseCustomException {
    public UnauthorizedTemplateAccessException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.UNAUTHORIZED_TEMPLATE_ACCESS;
    }
}
