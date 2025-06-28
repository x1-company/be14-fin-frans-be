package com.x1.frans.purchaseorder.command.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderUpdateRequestDto {
    private String title;
    private LocalDate requestedDeliveryDate;
    private List<PurchaseOrderProductUpdateDto> products;
}