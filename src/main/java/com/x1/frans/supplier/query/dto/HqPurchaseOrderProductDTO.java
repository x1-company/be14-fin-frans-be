package com.x1.frans.supplier.query.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HqPurchaseOrderProductDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private String purchaseUnit;
}
