package com.x1.frans.order.command.application.dto;

import com.x1.frans.order.command.domain.vo.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusUpdateRequestDto {

    private OrderStatus status;
}
