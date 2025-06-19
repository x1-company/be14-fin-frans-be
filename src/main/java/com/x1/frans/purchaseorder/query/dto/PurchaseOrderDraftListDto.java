package com.x1.frans.purchaseorder.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOrderDraftListDto {
    private Long id;
    private String code;
    private String products;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDate requestedDeliveryDate;
}