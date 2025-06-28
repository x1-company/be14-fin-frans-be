package com.x1.frans.purchaseorder.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderStatusUpdateRequestDto {
    private String status;
}