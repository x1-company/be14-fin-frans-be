package com.x1.frans.security.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestLoginVO {

    private String userCode;
    private String password;

}
