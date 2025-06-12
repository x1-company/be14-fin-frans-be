package com.x1.frans.warehouse.command.application.controller;

import com.x1.frans.warehouse.command.application.service.WarehouseCommandServiceImpl;
import com.x1.frans.warehouse.command.application.service.dto.WarehouseCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/warehouses")
@RequiredArgsConstructor
@Tag(name = "📦 창고", description = "warehouse")
public class WarehouseCommandController {

    private final WarehouseCommandServiceImpl warehouseCommandServiceImpl;

    @PostMapping
    @Operation(
            summary = "창고 등록",
            description = "창고 정보를 등록합니다. code(중복불가), name, address, 담당자 userId 필수."
    )
    public ResponseEntity<Long> create(@RequestBody WarehouseCreateCommand command) {
        Long id = warehouseCommandServiceImpl.create(command);
        return ResponseEntity.ok(id);
    }
}