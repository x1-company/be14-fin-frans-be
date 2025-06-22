package com.x1.frans.supplier.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestedDeliveryInfoDTO {

    private Long purchaseOrderId;
    private String purchaseOrderCode;
    private BigDecimal totalAmount;
    private LocalDate requestedDeliveryDate;
    private LocalDate processedAt;
    private Long deliveryInfoId;
    private Integer deliveredDay;
    private Integer deliveredMonth;
    private Integer deliveredYear;
}
