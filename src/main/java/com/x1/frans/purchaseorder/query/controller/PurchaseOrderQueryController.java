package com.x1.frans.purchaseorder.query.controller;

import com.x1.frans.purchaseorder.query.dto.PurchaseOrderRequestPendingListDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDraftListDto;
import com.x1.frans.purchaseorder.query.service.PurchaseOrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hq/purchaseOrder")
public class PurchaseOrderQueryController {
    private final PurchaseOrderQueryService service;

    public PurchaseOrderQueryController(PurchaseOrderQueryService service) {
        this.service = service;
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PurchaseOrderDraftListDto>> getDrafts() {
        List<PurchaseOrderDraftListDto> result = service.getDraftList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "발주 대기인 발주 목록 조회", description = "사용자 본인의 발주 대기인 발주 목록을 조회합니다.")
    @GetMapping("/requestPending")
    public ResponseEntity<List<PurchaseOrderRequestPendingListDto>> getRequestPending(@AuthenticationPrincipal CustomUserDetails user) {

        List<PurchaseOrderRequestPendingListDto> result = service.getRequestPending(user.getUserId());
        return ResponseEntity.ok(result);
    }
}
