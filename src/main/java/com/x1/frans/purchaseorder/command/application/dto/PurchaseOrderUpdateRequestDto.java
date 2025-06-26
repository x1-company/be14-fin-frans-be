package com.x1.frans.purchaseorder.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderUpdateRequestDto {
    private String title;
    private LocalDate requestedDeliveryDate;
    private List<PurchaseOrderProductUpdateDto> products;
}