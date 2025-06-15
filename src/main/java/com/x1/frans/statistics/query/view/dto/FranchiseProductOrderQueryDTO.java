package com.x1.frans.statistics.query.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseProductOrderQueryDTO {

    private int orderQuantity;
    private long productId;
    private String productCode;
    private String productName;
    private BigDecimal productPurchasePrice;
    private BigDecimal productSalePrice;
    private String productPurchaseUnit;
    private String productUnit;
    private String productSpec;
    private String productAttributeName;
    private String productGroupName;
    private String productTypeName;
}
