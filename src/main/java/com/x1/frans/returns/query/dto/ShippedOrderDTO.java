package com.x1.frans.returns.query.dto;

import com.x1.frans.order.command.domain.vo.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippedOrderDTO {

    private Long id;
    private String code;
    private LocalDate deliveredAt;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
