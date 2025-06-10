package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class FranchiseNotFoundException extends BaseCustomException {
    public FranchiseNotFoundException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.ENTITY_NOT_FOUND;
  }
}
