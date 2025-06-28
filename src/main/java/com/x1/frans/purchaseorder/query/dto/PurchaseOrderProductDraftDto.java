package com.x1.frans.purchaseorder.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderProductDraftDto {
    private Long id;
    private Long productId;
    private String name;
    private Integer quantity;
    private String remarks;
}