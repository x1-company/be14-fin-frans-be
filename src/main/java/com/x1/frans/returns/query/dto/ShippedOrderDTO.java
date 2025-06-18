package com.x1.frans.returns.query.dto;

import com.x1.frans.order.command.domain.vo.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "1주일 이내 배송완료된 주문서 목록 조회")
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
