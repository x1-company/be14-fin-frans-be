package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class InvalidApprovalStateException extends BaseCustomException {
    public InvalidApprovalStateException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.INVALID_APPROVAL_STATE;
  }
}
