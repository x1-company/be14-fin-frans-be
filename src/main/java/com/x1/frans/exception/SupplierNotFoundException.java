package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class SupplierNotFoundException extends BaseCustomException {
    public SupplierNotFoundException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.SUPPLIER_NOT_FOUND;
  }
}
