package com.x1.frans.purchase.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseRequestProductUpdateCommand {
    private Long id; // 구매 요청 자재 id
    private Integer quantity;
    private String remarks;
    private Long productId;
}