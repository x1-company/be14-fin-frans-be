package com.x1.frans.purchaseorder.command.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderProductUpdateDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String remarks;
    private Long purchaseRequestId;
}