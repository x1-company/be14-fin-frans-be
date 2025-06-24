package com.x1.frans.purchase.command.application.service.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PurchaseRequestCreateCommand {
    private Long draftId;
    private String title;
    private String description;
    private LocalDate requestedDeliveryDate;
    private List<PurchaseRequestProductCreateCommand> products;
    private Boolean isRequested; // 값이 없으면 true
}