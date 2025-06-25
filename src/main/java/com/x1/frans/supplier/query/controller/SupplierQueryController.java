package com.x1.frans.supplier.query.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.query.dto.MyInfoDTO;
import com.x1.frans.supplier.query.service.SupplierQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐔 공급처", description = "공급처 관련 API")
@RestController
@RequestMapping("/api/supplier")
public class SupplierQueryController {

    private final SupplierQueryService supplierQueryService;

    @Autowired
    public SupplierQueryController(SupplierQueryService supplierQueryService) {
        this.supplierQueryService = supplierQueryService;
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "내 정보를 조회합니다."
    )
    public ResponseEntity<MyInfoDTO> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        MyInfoDTO myInfo = supplierQueryService.getMyInfo(customUserDetails.getUserId());

        return ResponseEntity.ok(myInfo);
    }
}
