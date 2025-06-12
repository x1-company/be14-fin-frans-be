package com.x1.frans.product.command.application.controller;

import com.x1.frans.product.command.application.service.ProductCommandService;
import com.x1.frans.product.command.application.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/hq/products")
@Tag(name = "🍗 자재", description = "product")
public class ProductCommandController {
    private final ProductCommandService productCommandService;

    @Autowired
    public ProductCommandController(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }
    
    @PostMapping
    @Operation(
            summary = "자재 등록",
            description = "본사 자재를 등록합니다. active를 제외한 나머지 필드는 필수이며(active는 기본적으로 true), code 중복 등록 불가."
    )
    public ResponseEntity<Long> create(@RequestBody ProductCreateCommand command) {
        long id = productCommandService.create(command);
        return ResponseEntity.ok(id);
    }

    /** 자재 수정 */
    @PutMapping
    @Operation(
            summary = "자재 정보 전체 수정",
            description = "기존 자재 정보를 전체 수정합니다. ID는 필수, 나머지 필드도 모두 전달해야 함."
    )
    public ResponseEntity<Void> update(@RequestBody ProductUpdateCommand command) {
        productCommandService.update(command);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{id}/active")
    @Operation(
            summary = "자재 활성/비활성 처리",
            description = "특정 자재를 활성(사용)/비활성(미사용) 상태로 변경합니다. {id}는 자재 ID, body에 active boolean 값 전달."
    )
    public ResponseEntity<Void> changeActive(
            @PathVariable long id,
            @RequestBody ProductActiveCommand command
    ) {
        productCommandService.changeActive(id, command.isActive());
        return ResponseEntity.ok().build();
    }
}