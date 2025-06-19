package com.x1.frans.purchaseorder.command.application.controller;

import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderSaveRequestDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderUpdateRequestDto;
import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderEntity;
import com.x1.frans.purchaseorder.command.application.service.PurchaseOrderCommandService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hq/purchaseorder")
@RequiredArgsConstructor
@Tag(name = "📝 발주", description = "PurchaseOrder")
public class PurchaseOrderCommandController {

    private final PurchaseOrderCommandService purchaseOrderCommandService;

    @PostMapping("/draft")
    @Operation(summary = "발주 임시저장", description = "발주 등록 시 임시저장한다.")
    public ResponseEntity<Long> saveDraft(
            @RequestBody PurchaseOrderSaveRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long orderId = purchaseOrderCommandService.saveDraft(dto, userDetails.getUserId());
        return ResponseEntity.ok(orderId);
    }

    @PutMapping("/{purchaseOrderId}/draft")
    @Operation(summary = "발주 임시저장 수정", description = "발주 임시저장한 내용을 수정한다.")
    public ResponseEntity<Void> updateDraft(
            @PathVariable("purchaseOrderId") Long orderId,
            @RequestBody PurchaseOrderUpdateRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        purchaseOrderCommandService.updateDraft(orderId, dto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{purchaseOrderId}")
    @Operation(summary = "발주 삭제", description = "발주 임시저장/발주 대기 상태에서만 발주를 삭제한다.")
    public ResponseEntity<Void> delete(
            @PathVariable("purchaseOrderId") Long purchaseOrderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        purchaseOrderCommandService.delete(purchaseOrderId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{purchaseOrderId}/request")
    @Operation(summary = "임시저장 발주 정식 등록", description = "임시저장 상태의 발주를 정식으로 등록ㄱ한다.")
    public ResponseEntity<Void> requestOrder(
            @PathVariable Long purchaseOrderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        purchaseOrderCommandService.requestOrder(purchaseOrderId, userId);
        return ResponseEntity.ok().build();
    }
}