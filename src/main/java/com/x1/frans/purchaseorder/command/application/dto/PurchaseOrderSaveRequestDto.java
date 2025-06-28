package com.x1.frans.purchaseorder.command.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderSaveRequestDto {
    private String title;
    private Long supplierId;
    private LocalDate requestedDeliveryDate;
    private List<PurchaseOrderProductCreateDto> products;
}