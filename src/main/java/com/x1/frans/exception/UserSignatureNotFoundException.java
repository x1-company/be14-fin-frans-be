package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class UserSignatureNotFoundException extends BaseCustomException {
    public UserSignatureNotFoundException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.USER_SIGNATURE_NOT_FOUND;
  }
}
