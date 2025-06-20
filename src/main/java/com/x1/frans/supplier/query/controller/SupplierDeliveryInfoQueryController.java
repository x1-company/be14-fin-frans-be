package com.x1.frans.supplier.query.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.query.dto.SupplierDeliveryInfoDTO;
import com.x1.frans.supplier.query.service.SupplierDeliveryInfoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐔 납품서 조회", description = "공급처에서 작성한 납품서를 조회하는 API")
@Slf4j
@RestController
@RequestMapping("/api/supplier/delivery-info")
public class SupplierDeliveryInfoQueryController {

    private SupplierDeliveryInfoQueryService supplierDeliveryInfoQueryService;

    @Autowired
    public SupplierDeliveryInfoQueryController(SupplierDeliveryInfoQueryService supplierDeliveryInfoQueryService) {
        this.supplierDeliveryInfoQueryService = supplierDeliveryInfoQueryService;
    }

    @GetMapping("/list")
    @Operation(summary = "납품서 목록 조회", description = "납품서 목록을 연, 월, 일로 필터링하여 조회하는 API")
    public ResponseEntity<List<SupplierDeliveryInfoDTO>> getDeliveryInfos(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day
    ) {
        Long supplierId = customUserDetails.getSupplierId();

        List<SupplierDeliveryInfoDTO> results = supplierDeliveryInfoQueryService.findDeliveryInfos(supplierId, year, month, day);

        return ResponseEntity.ok(results);
    }

}
