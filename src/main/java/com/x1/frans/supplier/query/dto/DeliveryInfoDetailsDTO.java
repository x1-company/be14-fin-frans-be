package com.x1.frans.supplier.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryInfoDetailsDTO {

    private Long purchaseOrderId;
    private String purchaseOrderCode;
    private BigDecimal totalAmount;
    private LocalDate requestedDeliveryDate;
    private String deliveryCompanyName;
    private String vehicleNumber;
    private String trackingNumber;
    private LocalDate expectedDate;
    private Long deliveryInfoId;
    private List<PurchaseOrderProductDTO> productList;
}
