package com.x1.frans.purchase.query.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestSimpleDto {
    private Long id;
    private String code;
    private String title;
    private String status;
    private LocalDate requestedDeliveryDate;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}