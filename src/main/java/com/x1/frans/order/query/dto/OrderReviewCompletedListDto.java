package com.x1.frans.order.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderReviewCompletedListDto {

    private Long id;
    private String code;
    private String status;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String franchiseName;

}
