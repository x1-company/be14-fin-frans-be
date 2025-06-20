package com.x1.frans.supplier.query.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.query.dto.SupplierDeliveryProductDTO;
import com.x1.frans.supplier.query.service.SupplierDeliveryProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐔 공급처 납품 자재", description = "납품서에 저장할 자재를 조회하는 API")
@Slf4j
@RestController
@RequestMapping("/api/supplier/delivery-detail-products")
public class SupplierDeliveryProductQueryController {

    private final SupplierDeliveryProductService supplierDeliveryDetailService;

    @Autowired
    public SupplierDeliveryProductQueryController(SupplierDeliveryProductService supplierDeliveryDetailService) {
        this.supplierDeliveryDetailService = supplierDeliveryDetailService;
    }

    @GetMapping
    @Operation(
            summary = "납품 상세에 등록할 자재 조회",
            description = "본사와 거래 중인 자재 목록을 전체, 이름 또는 코드 기준으로 조회한다.")
    public ResponseEntity<List<SupplierDeliveryProductDTO>> getAllProductsBySupplier(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long supplierId = customUserDetails.getSupplierId();
        List<SupplierDeliveryProductDTO> productList;

        boolean hasName = name != null && !name.isBlank();
        boolean hasCode = code != null && !code.isBlank();

        if (!hasName && !hasCode) {
            productList = supplierDeliveryDetailService.getAllProductsBySupplier(supplierId);
        } else if (hasName && hasCode) {
            productList = supplierDeliveryDetailService.getProductsByNameAndCode(supplierId, name, code);
        } else if (hasName) {
            productList = supplierDeliveryDetailService.getProductsByName(supplierId, name);
        } else {
            productList = supplierDeliveryDetailService.getProductsByCode(supplierId, code);
        }

        return ResponseEntity.ok(productList);
    }


}
