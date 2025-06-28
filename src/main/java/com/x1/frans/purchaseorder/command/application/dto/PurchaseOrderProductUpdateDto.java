package com.x1.frans.purchaseorder.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderProductUpdateDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String remarks;
    private Long purchaseRequestId;
}