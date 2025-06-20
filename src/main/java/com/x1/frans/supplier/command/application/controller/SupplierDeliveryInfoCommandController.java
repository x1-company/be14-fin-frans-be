package com.x1.frans.supplier.command.application.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.command.application.dto.DeliveryInfoCreateRequestDTO;
import com.x1.frans.supplier.command.application.dto.DeliveryInfoModifyDTO;
import com.x1.frans.supplier.command.application.service.SupplierDeliveryInfoCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐔 공급처", description = "공급처가 납품서를 등록하는 API")
@Slf4j
@RestController
@RequestMapping("/api/supplier/delivery-infos")
public class SupplierDeliveryInfoCommandController {

    private final SupplierDeliveryInfoCommandService supplierDeliveryInfoCommandService;

    @Autowired
    public SupplierDeliveryInfoCommandController (
            SupplierDeliveryInfoCommandService supplierDeliveryInfoCommandService) {
        this.supplierDeliveryInfoCommandService = supplierDeliveryInfoCommandService;
    }

    @PostMapping
    @Operation(summary = "납품서 등록", description = "공급처에서 납품서를 등록하는 API")
    public ResponseEntity<Long> registerDeliveryInfo(
            @RequestBody @Valid DeliveryInfoCreateRequestDTO requestDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long supplierId = customUserDetails.getSupplierId();
        String supplierCode = customUserDetails.getSupplierCode();

        Long deliveryInfoId
                = supplierDeliveryInfoCommandService.createDeliveryInfo(supplierId, supplierCode, requestDTO);

        return ResponseEntity.ok(deliveryInfoId);
    }

    @PatchMapping("/modify")
    @Operation(summary = "납품서 수정 (부분 수정)", description = "공급처 또는 본사 직원이 납품서의 일부 정보를 수정합니다.")
    public ResponseEntity<Void> modifyDeliveryInfo(
            @RequestBody @Valid DeliveryInfoModifyDTO modifyDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long supplierId = customUserDetails.getSupplierId();
        String supplierCode = customUserDetails.getSupplierCode();

        supplierDeliveryInfoCommandService.modifyDeliveryInfo(modifyDTO, supplierId, supplierCode);

        return ResponseEntity.ok().build();
    }


}
