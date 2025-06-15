package com.x1.frans.purchase.command.application.service.dto;

import lombok.Getter;

@Getter
public class PurchaseRequestProductCreateCommand {
    private Long productId;
    private Integer quantity;
    private String remarks;
}