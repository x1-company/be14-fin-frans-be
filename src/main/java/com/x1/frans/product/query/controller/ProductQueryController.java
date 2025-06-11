package com.x1.frans.product.query.controller;

import com.x1.frans.product.query.dto.ProductDetailDTO;
import com.x1.frans.product.query.dto.ProductListDTO;
import com.x1.frans.product.query.service.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐓 자재", description = "product")
@Slf4j
@RestController
@RequestMapping("/api/hq/products")
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @Autowired
    public ProductQueryController (ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @Operation(
            summary = "자재 목록 조회",
            description = "자재 목록을 필터를 사용하여 조회할 수 있다. 필터 항목으로는 자재 구분, 자재 분류, 자재 속성, 자재 사용 여부가 있다."
    )
    @GetMapping("/list")
    public ResponseEntity<List<ProductListDTO>> getProductList (
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Long productGroupId,
            @RequestParam(required = false) Long productAttributeId,
            @RequestParam(required = false) Boolean isActive
    ) {
        List<ProductListDTO> productList =
                productQueryService.getProductsByFilter (
                        productTypeId, productGroupId, productAttributeId, isActive);

        return ResponseEntity.ok(productList);
    }

    @Operation(summary = "자재 코드로 조회", description = "자재 코드로 해당 자재를 검색할 수 있다.")
    @GetMapping("/code/{code}")
    public ResponseEntity<ProductListDTO> getProductByCode (@PathVariable String code) {
        ProductListDTO productList =
                productQueryService.getProductByCode(code);

        return ResponseEntity.ok(productList);
    }

    @Operation(summary = "자재명으로 조회", description = "자재명으로 해당 자제를 검색할 수 있다.")
    @GetMapping("name/{name}")
    public ResponseEntity<List<ProductListDTO>> getProductListByName (@PathVariable String name) {
        List<ProductListDTO> productList =
                productQueryService.getProductsByName(name);

        return ResponseEntity.ok(productList);
    }

    @Operation(summary = "공급처명으로 조회", description = "공급처명으로 해당 공급처와 거래하는 자재 목록을 검색할 수 있다.")
    @GetMapping("supplier-name/{supplierName}")
    public ResponseEntity<List<ProductListDTO>> getProductListBySupplierName (@PathVariable String supplierName) {
          List<ProductListDTO> productList =
                productQueryService.getProductsBySupplierName(supplierName);

        return ResponseEntity.ok(productList);
    }

    @Operation(summary = "자재 상세 조회", description = "자재 ID로 자재 상세 정보를 조회합니다.")
    @GetMapping("/details/{id}")
    public ProductDetailDTO getProductDetail(@PathVariable Long id) {

        return productQueryService.getProductDetailById(id);
    }
}
