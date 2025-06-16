package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidFilUploadTypeException extends BaseCustomException {
    public InvalidFilUploadTypeException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_FILE_UPLOAD_TYPE;
    }
}
