package com.x1.frans.purchaseorder.query.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderProductDetailDto {
    private Long id;
    private int no;
    private String productCode;
    private String productName;
    private BigDecimal purchasePrice;
    private int quantity;
    private String purchaseUnit;
    private String standard;
    private String productTypeName;
    private String productGroupName;
    private String productAttributeName;
}