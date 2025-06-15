package com.x1.frans.warehouse.command.application.service;

import com.x1.frans.warehouse.command.application.service.dto.WarehouseCreateCommand;
import com.x1.frans.warehouse.command.application.service.dto.WarehouseUpdateCommand;

public interface WarehouseCommandService {
  
    Long create(WarehouseCreateCommand command, Long departmentId);
    void update(WarehouseUpdateCommand command, Long departmentId);

}