package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class AwsFIleUploadException extends BaseCustomException {
    public AwsFIleUploadException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.FILE_UPLOAD_FAILED;
  }
}
