package com.x1.frans.order.command.application.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FranchiseOrderCreateRequestDto {
    private Long franchiseId;
    private List<OrderMaterialRequestDto> materials;
}
