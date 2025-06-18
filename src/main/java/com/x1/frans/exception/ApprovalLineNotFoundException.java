package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ApprovalLineNotFoundException extends BaseCustomException {

    public ApprovalLineNotFoundException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.APPROVAL_LINE_NOT_FOUND;
  }
}
