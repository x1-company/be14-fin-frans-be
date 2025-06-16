package com.x1.frans.product.query.controller;

import com.x1.frans.product.query.dto.ProductListDTO;
import com.x1.frans.product.query.service.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "🍗 자재 (가맹점용)", description = "product")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/franchise/products")
public class FranchiseProductQueryController {

    private final ProductQueryService productQueryService;

    @Operation(summary = "자재명으로 조회", description = "자재명으로 해당 자제를 검색할 수 있다.")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductListDTO>> getProductListByName(@PathVariable String name) {
        List<ProductListDTO> productList =
                productQueryService.getProductsByName(name);

        return ResponseEntity.ok(productList);
    }
}
