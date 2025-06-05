package com.x1.frans.product.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateCommand {
    private String code;
    private String name;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String unit;
    private String spec;
    private boolean isActive;

    private int supplierId;
    private int productGroupId;
    private int productTypeId;
    private int productAttributeId;
}
