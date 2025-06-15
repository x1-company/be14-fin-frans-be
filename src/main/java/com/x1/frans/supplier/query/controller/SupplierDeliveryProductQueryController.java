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
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Operation(summary = "납품 상세에 등록할 자재 조회", description = "본사와 거래 중인 자재 목록 조회")
    public ResponseEntity<List<SupplierDeliveryProductDTO>> getAllProductsBySupplier(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long supplierId = customUserDetails.getSupplierId();
        List<SupplierDeliveryProductDTO> productList
                = supplierDeliveryDetailService.getAllProductsBySupplier(supplierId);

        return ResponseEntity.ok(productList);
    }

}
