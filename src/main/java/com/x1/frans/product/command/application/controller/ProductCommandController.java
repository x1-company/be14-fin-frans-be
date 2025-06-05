package com.x1.frans.product.command.application.controller;

import com.x1.frans.product.command.application.service.ProductCommandService;
import com.x1.frans.product.command.application.service.dto.ProductCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductCommandService productCommandService;

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody ProductCreateCommand command) {
        int id = productCommandService.create(command);
        return ResponseEntity.ok(id);
    }
}