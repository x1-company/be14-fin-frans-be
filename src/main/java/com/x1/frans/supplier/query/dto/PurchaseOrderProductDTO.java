package com.x1.frans.supplier.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseOrderProductDTO {

    private String productName;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private String purchaseUnit;
}
