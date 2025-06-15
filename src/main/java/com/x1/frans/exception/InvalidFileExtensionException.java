package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidFileExtensionException extends BaseCustomException {
    public InvalidFileExtensionException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_FILE_EXTENSION;
    }
}
