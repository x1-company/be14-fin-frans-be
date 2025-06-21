package com.x1.frans.supplier.query.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.query.dto.DeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.RequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.SupplierDeliveryInfoDTO;
import com.x1.frans.supplier.query.service.SupplierDeliveryInfoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "🐔 납품서 조회", description = "공급처에서 작성한 납품서를 조회하는 API")
@Slf4j
@RestController
@RequestMapping("/api/supplier/delivery-info")
public class SupplierDeliveryInfoQueryController {

    private final SupplierDeliveryInfoQueryService supplierDeliveryInfoQueryService;

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

    @GetMapping("/orders/{type}")
    @Operation(
            summary = "납품 정보 등록이 필요하거나 등록된 발주 목록 조회",
            description = "type: requested, completed"
    )
    public ResponseEntity<List<RequestedDeliveryInfoDTO>> getRequestedPurchaseOrders(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String type) {

        Long supplierId = customUserDetails.getSupplierId();

        List<RequestedDeliveryInfoDTO> requestedOrders = supplierDeliveryInfoQueryService
                .getRequestedPurchaseOrders(supplierId, type);

        return ResponseEntity.ok(requestedOrders);
    }

    @GetMapping("/order/{purchaseOrderId}")
    @Operation(
            summary = "납품 정보 등록이 필요한 발주 상세 조회",
            description = "결재가 완료된 해당 공급처의 발주 상세 조회"
    )
    public ResponseEntity<DeliveryInfoDetailsDTO> getPurchaseOrderDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                         @PathVariable Long purchaseOrderId) {

        Long supplierId = customUserDetails.getSupplierId();

        DeliveryInfoDetailsDTO details = supplierDeliveryInfoQueryService
                .getPurchaseOrderDetail(purchaseOrderId, supplierId);

        return ResponseEntity.ok(details);
    }

}
