package com.x1.frans.purchaseorder.query.controller;

import com.x1.frans.purchaseorder.enums.PurchaseOrderStatus;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderSimpleDto;
import com.x1.frans.purchaseorder.query.service.PurchaseOrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/purchaseorder")
@RequiredArgsConstructor
@Tag(name = "📦 발주", description = "PurchaseOrder")
public class PurchaseOrderQueryController {

    private final PurchaseOrderQueryService purchaseOrderQueryService;

    @GetMapping("/draft")
    @Operation(summary = "발주 임시저장 목록 조회", description = "임시저장된 발주 목록을 조회한다.")
    public Page<PurchaseOrderSimpleDto> getDraftOrder(Pageable pageable) {
        return purchaseOrderQueryService.getDraftOrder(pageable);
    }

    @GetMapping("/draft/{id}")
    @Operation(summary = "발주 임시저장 상세 조회", description = "발주 ID를 기준으로 상세 정보를 조회한다.")
    public PurchaseOrderDetailDto getDraftOrderDetail(@PathVariable Long id) {
        return purchaseOrderQueryService.getDraftOrderDetail(id);
    }

    @GetMapping
    @Operation(summary = "발주 목록 조회", description = "모든 발주(임시저장 제외) 목록을 조회한다.")
    public ResponseEntity<Page<PurchaseOrderSimpleDto>> getOrder(
            @RequestParam("supplierId") Long supplierId,
            Pageable pageable
    ) {
        Page<PurchaseOrderSimpleDto> page = purchaseOrderQueryService.getOrder(supplierId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "발주 상세 조회", description = "발주 ID로 상세 정보를 조회한다.")
    public PurchaseOrderDetailDto getOrderDetail(@PathVariable Long id) {
        return purchaseOrderQueryService.getOrderDetail(id);
    }

    @GetMapping("/status")
    @Operation(summary = "발주 상태로 발주 조회", description = "발주 상태로 발주 목록을 조회한다.")
    public Page<PurchaseOrderSimpleDto> getOrderByStatus(
            @RequestParam("status") PurchaseOrderStatus status,
            Pageable pageable
    ) {
        return purchaseOrderQueryService.getOrderByStatus(status, pageable);
    }

    @GetMapping("/code")
    @Operation(summary = "발주번호로 발주 조회", description = "발주번호(code)로 발주 목록을 조회한다.")
    public Page<PurchaseOrderSimpleDto> getOrderByCode(
            @RequestParam("code") String code,
            Pageable pageable
    ) {
        return purchaseOrderQueryService.getOrderByCode(code, pageable);
    }

    @GetMapping("/title")
    @Operation(summary = "발주 제목으로 목록 조회", description = "발주 제목에 포함된 내용으로 목록을 조회한다.")
    public Page<PurchaseOrderSimpleDto> getOrderByTitle(
            @RequestParam("title") String title,
            Pageable pageable
    ) {
        return purchaseOrderQueryService.getOrderByTitle(title, pageable);
    }
}