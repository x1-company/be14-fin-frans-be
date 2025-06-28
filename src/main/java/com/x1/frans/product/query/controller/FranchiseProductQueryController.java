package com.x1.frans.product.query.controller;

import com.x1.frans.product.query.dto.ProductListDTO;
import com.x1.frans.product.query.service.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "자재 목록 조회",
            description = "자재 목록을 필터를 사용하여 조회할 수 있다. 필터 항목으로는 자재 구분, 자재 분류, 자재 속성, 자재 사용 여부가 있다."
    )
    @GetMapping("/list")
    public ResponseEntity<List<ProductListDTO>> getProductList(
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Long productGroupId,
            @RequestParam(required = false) Long productAttributeId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Long supplierId
    ) {
        List<ProductListDTO> productList =
                productQueryService.getProductsByFilter(
                        productTypeId, productGroupId, productAttributeId, isActive, supplierId);

        return ResponseEntity.ok(productList);
    }
}
