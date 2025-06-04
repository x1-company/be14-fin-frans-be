package com.x1.frans.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", HttpStatus.CONFLICT),
    EMAIL_SEND_FAILED("EMAIL_SEND_FAILED", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USER_TYPE("INVALID_USER_TYPE", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS("INVALID_ADDRESS", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus httpStatus;
}
