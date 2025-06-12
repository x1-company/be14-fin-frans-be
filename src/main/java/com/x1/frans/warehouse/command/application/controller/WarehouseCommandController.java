package com.x1.frans.warehouse.command.application.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.warehouse.command.application.service.WarehouseCommandService;
import com.x1.frans.warehouse.command.application.service.dto.WarehouseCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/warehouses")
@Tag(name = "📦 창고", description = "warehouse")
public class WarehouseCommandController {

    private final WarehouseCommandService warehouseCommandService;

    @Autowired
    public WarehouseCommandController(WarehouseCommandService warehouseCommandService) {
        this.warehouseCommandService = warehouseCommandService;
    }

    @PostMapping
    @Operation(
            summary = "창고 등록",
            description = "창고 정보를 등록합니다. code(중복불가), name, address, 담당자 userId 필수."
    )
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody WarehouseCreateCommand command
    ) {
        Long id = warehouseCommandService.create(command, userDetails.getDepartmentId());
        return ResponseEntity.ok(id);
    }
}