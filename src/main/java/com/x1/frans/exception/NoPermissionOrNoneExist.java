package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class NoPermissionOrNoneExist extends BaseCustomException {
    public NoPermissionOrNoneExist(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_PERMISSION_OR_NONE_EXIST;
    }

}
