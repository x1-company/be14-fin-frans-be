package com.x1.frans.warehouse.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseUpdateCommand {
    private Long id;           // 수정 대상 창고 id (PK)
    private String name;       // 창고 이름
    private String address;    // 창고 주소
    private Long userId;       // 담당자 id (필수, 변경 가능)
}