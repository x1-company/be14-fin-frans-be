package com.x1.frans.purchase.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseRequestUpdateCommand {
    private Long id;    // 구매 요청 목록 id
    private String title;
    private String description;
    private Boolean isRequested;
    private LocalDate requestedDeliveryDate;
    private List<PurchaseRequestProductUpdateCommand> products; // 자재 수정 목록
}