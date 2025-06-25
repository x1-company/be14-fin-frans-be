package com.x1.frans.supplier.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HqRequestedDeliveryInfoDTO {

    private Long purchaseOrderId;
    private String purchaseOrderCode;
    private BigDecimal totalAmount;
    private LocalDate requestedDeliveryDate;
    private LocalDate processedAt;
    private Long deliveryInfoId;
    private Integer deliveredDay;
    private Integer deliveredMonth;
    private Integer deliveredYear;
    private Long supplierId;

}
