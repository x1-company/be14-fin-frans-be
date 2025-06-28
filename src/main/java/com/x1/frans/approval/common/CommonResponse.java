package com.x1.frans.approval.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
//@NoArgsConstructor
public class CommonResponse<T> {
    private boolean success;
    private T data;
    private String message;

    // 성공 응답 생성
    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>(true, data, message);
    }

}
