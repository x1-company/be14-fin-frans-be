package com.x1.frans.purchaseorder.query.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderSimpleDto {
    private Long id;
    private String code;
    private String title;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDate requestedDeliveryDate;
    private Long supplierId;
}