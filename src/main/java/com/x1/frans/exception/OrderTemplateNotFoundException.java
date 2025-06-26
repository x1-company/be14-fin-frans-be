package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class OrderTemplateNotFoundException extends BaseCustomException {

    public OrderTemplateNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_TEMPLATE_NOT_FOUND;
    }
}
