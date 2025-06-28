package com.x1.frans.purchaseorder.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOrderRequestPendingListDto {

    private Long id;
    private String code;
    private String supplierName;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
