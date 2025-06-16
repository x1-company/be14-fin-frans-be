package com.x1.frans.purchase.query.controller;

import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import com.x1.frans.purchase.query.service.PurchaseRequestQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/purchase/requests")
@RequiredArgsConstructor
@Tag(name = "📝 구매요청", description = "PurchaseRequest")
public class PurchaseRequestQueryController {

    private final PurchaseRequestQueryService purchaseRequestQueryService;

    /** 임시저장 목록 조회 */
    @GetMapping("/draft")
    @Operation(summary = "구매 요청 임시저장 목록 조회", description = "임시저장된 구매 요청 목록을 조회한다.")
    public Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable) {
        return purchaseRequestQueryService.getDraftPurchaseRequests(pageable);
    }

    /** 임시저장 목록 상세조회 */
    @GetMapping("/draft/{id}")
    @Operation(summary = "구매 요청 임시저장 목록 상세 조회", description = "임시저장된 구매 요청 목록을 상세 조회한다.")
    public PurchaseRequestDetailDto getDraftPurchaseRequestDetail(@PathVariable Long id) {
        return purchaseRequestQueryService.getDraftDetail(id);
    }
}