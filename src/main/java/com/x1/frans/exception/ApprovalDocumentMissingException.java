package com.x1.frans.exception;

import com.x1.frans.exception.enums.ErrorCode;

public class ApprovalDocumentMissingException extends BaseCustomException {
    public ApprovalDocumentMissingException(String message) {
        super(message);
    }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.APPROVAL_DOCUMENT_MISSING;
  }
}
