package com.x1.frans.product.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateCommand {
    private long id;
    private String code;
    private String name;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String unit;
    private String spec;
    private boolean active;
    private long supplierId;
    private long productGroupId;
    private long productTypeId;
    private long productAttributeId;
}