package com.x1.frans.product.command.application.controller;

import com.x1.frans.product.command.application.service.ProductCommandService;
import com.x1.frans.product.command.application.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/products")
@RequiredArgsConstructor
public class ProductCommandController {
    private final ProductCommandService productCommandService;

    /** 자재 등록 */
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProductCreateCommand command) {
        long id = productCommandService.create(command);
        return ResponseEntity.ok(id);
    }

    /** 자재 수정 */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProductUpdateCommand command) {
        productCommandService.update(command);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> changeActive(
            @PathVariable long id,
            @RequestBody ProductActiveCommand command
    ) {
        productCommandService.changeActive(id, command.isActive());
        return ResponseEntity.ok().build();
    }
}