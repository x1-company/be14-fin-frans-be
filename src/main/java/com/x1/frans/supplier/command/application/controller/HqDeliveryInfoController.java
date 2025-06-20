package com.x1.frans.supplier.command.application.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.command.application.dto.ConfirmDeliveryDateRequestDTO;
import com.x1.frans.supplier.command.application.service.HqDeliveryInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐔납품 확정", description = "본사가 납품일을 확정 짓는 API")
@Slf4j
@RestController
@RequestMapping("/api/hq/delivery-infos")
public class HqDeliveryInfoController {

    private final HqDeliveryInfoService hqDeliveryInfoService;

    @Autowired
    public HqDeliveryInfoController(HqDeliveryInfoService hqDeliveryInfoService) {
        this.hqDeliveryInfoService = hqDeliveryInfoService;
    }

    @PatchMapping("/{id}")
    @Operation(summary = "납품일 확정", description = "공급처에서 등록한 납품서 정보에 납품이 확정된 이후 납품일 기입 API")
    public ResponseEntity<Void> confirmDeliveryDate(
            @PathVariable Long id,
            @RequestBody ConfirmDeliveryDateRequestDTO requestDTO) {

        hqDeliveryInfoService.confirmDeliveryDate(
                id,
                requestDTO.getYear(),
                requestDTO.getMonth(),
                requestDTO.getDay()
        );

        return ResponseEntity.ok().build();
    }

}
