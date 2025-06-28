package com.x1.frans.purchaseorder.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderDraftDetailDto {
    private Long id;
    private String code;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDate requestedDeliveryDate;
    private String userName;
    private String supplierName;

    private List<PurchaseOrderProductDraftDto> products;
}