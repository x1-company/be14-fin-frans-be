package com.x1.frans.purchase.command.application.controller;

import com.x1.frans.purchase.command.application.service.PurchaseRequestCommandService;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestCreateCommand;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestUpdateCommand;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hq/purchase/requests")
@RequiredArgsConstructor
@Tag(name = "📝 구매요청", description = "PurchaseRequest")
public class PurchaseRequestCommandController {

    private final PurchaseRequestCommandService purchaseRequestCommandService;

    @PostMapping
    @Operation(
            summary = "구매요청 등록", description = "구매 요청을 작성한다.")
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PurchaseRequestCreateCommand command
    ) {
        Long id = purchaseRequestCommandService.create(command, userDetails.getUserId());
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{purchaseRequestId}")
    @Operation(summary = "구매요청 수정", description = "구매요청을 수정한다.")
    public ResponseEntity<Void> update(
            @PathVariable Long purchaseRequestId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PurchaseRequestUpdateCommand command
    ) {
        purchaseRequestCommandService.update(purchaseRequestId, command, userDetails.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{purchaseRequestId}")
    @Operation(summary = "구매요청 삭제", description = "구매요청 및 관련 자재 전부 삭제")
    public ResponseEntity<Void> delete(
            @PathVariable Long purchaseRequestId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        purchaseRequestCommandService.delete(purchaseRequestId, userDetails.getUserId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{purchaseRequestId}/status")
    @Operation(summary = "구매요청 상태 변경", description = "구매요청의 상태를 검토중/승인/반려 등으로 변경")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long purchaseRequestId,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String statusStr = body.get("status");
        PurchaseRequestStatus status = PurchaseRequestStatus.valueOf(statusStr);
        purchaseRequestCommandService.changeStatus(purchaseRequestId, status, userDetails.getUserId());
        return ResponseEntity.ok().build();
    }
}