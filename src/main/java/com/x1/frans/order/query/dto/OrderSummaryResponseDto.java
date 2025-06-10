package com.x1.frans.order.query.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderSummaryResponseDto {
    private Long orderId;
    private String orderCode;
    private String productSummary;
    private String status;
    private LocalDateTime createdAt;
    private int totalAmount;
    private String franchiseName;
}
