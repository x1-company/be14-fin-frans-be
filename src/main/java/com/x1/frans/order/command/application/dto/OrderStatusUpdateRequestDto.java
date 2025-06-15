package com.x1.frans.order.command.application.dto;

import com.x1.frans.order.command.domain.vo.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderStatusUpdateRequestDto {

    @NotBlank(message = "주문 상태는 필수입니다.")
    private OrderStatus status;
}
