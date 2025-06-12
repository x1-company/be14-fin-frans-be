package com.x1.frans.warehouse.command.application.service;

import com.x1.frans.warehouse.command.application.service.dto.WarehouseCreateCommand;

public interface WarehouseCommandService {

    Long create(WarehouseCreateCommand command);
}
