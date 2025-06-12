package com.x1.frans.auth.command.application.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResetPasswordRequestVO {

    private String userCode;
}
