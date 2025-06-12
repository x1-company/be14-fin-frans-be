package com.x1.frans.returns.query.dto;

import com.x1.frans.order.command.domain.vo.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippedOrderDTO {

    private Long id;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
