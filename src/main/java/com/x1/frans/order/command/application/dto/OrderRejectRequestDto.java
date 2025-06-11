package com.x1.frans.order.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderRejectRequestDto {

    @NotBlank(message = "반려 사유는 필수입니다.")
    private String reason;
}
