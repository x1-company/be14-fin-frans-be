package com.x1.frans.exception.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

//    SUPPLIER_NOT_FOUND("SUPPLIER_NOT_FOUND", HttpStatus.NOT_FOUND),
//    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND),
//    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", HttpStatus.UNAUTHORIZED);

    SUPPLIER_NOT_FOUND("해당 공급처가 존재하지 않습니다."),
    USER_NOT_FOUND("해당 사용자가 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS("접근 권한이 없습니다.");

    private final String message;


    public String getMessage() {
        return message;
    }
}
