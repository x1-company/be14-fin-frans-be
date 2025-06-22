package com.x1.frans.supplier.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HqDeliveryInfoDetailsDTO {
    private Long purchaseOrderId;
    private String purchaseOrderCode;
    private BigDecimal totalAmount;
    private LocalDate requestedDeliveryDate;
    private String deliveryCompanyName;
    private String vehicleNumber;
    private String trackingNumber;
    private LocalDate expectedDate;
    private Long deliveryInfoId;
    private Long supplierId;
    private List<HqPurchaseOrderProductDTO> productList;
}
