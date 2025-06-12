package com.x1.frans.warehouse.command.application.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseCreateCommand {
    private String name;
    private String address;
}