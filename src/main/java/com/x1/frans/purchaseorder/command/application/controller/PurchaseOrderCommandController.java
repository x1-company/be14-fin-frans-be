package com.x1.frans.purchaseorder.command.application.controller;

import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderEntity;
import com.x1.frans.purchaseorder.command.application.service.PurchaseOrderCommandService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/purchaseorder")
@RequiredArgsConstructor
public class PurchaseOrderCommandController {

    private final PurchaseOrderCommandService purchaseOrderCommandService;

    @PostMapping("/draft")
    @Operation(summary = "발주 임시저장")
    public ResponseEntity<Long> saveDraft(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PurchaseOrderEntity entity
    ) {
        PurchaseOrderEntity saved = purchaseOrderCommandService.saveDraft(entity, userDetails.getUserId());
        return ResponseEntity.ok(saved.getId());
    }
}