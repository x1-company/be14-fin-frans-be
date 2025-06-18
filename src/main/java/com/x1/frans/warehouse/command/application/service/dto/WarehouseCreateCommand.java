package com.x1.frans.warehouse.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseCreateCommand {
    private String name;
    private String address;
    private Long userId;    // 창고 담당자 id(등록하는 사람과는 무관)
}