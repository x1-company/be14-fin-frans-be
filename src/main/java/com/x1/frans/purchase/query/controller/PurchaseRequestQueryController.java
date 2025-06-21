package com.x1.frans.purchase.query.controller;

import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import com.x1.frans.purchase.query.service.PurchaseRequestQueryServiceImpl;
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

    private final PurchaseRequestQueryServiceImpl purchaseRequestQueryServiceImpl;

    @GetMapping("/draft")
    @Operation(summary = "구매 요청 임시저장 목록 조회", description = "임시저장된 구매 요청 목록을 조회한다.")
    public Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable) {
        return purchaseRequestQueryServiceImpl.getDraftPurchaseRequests(pageable);
    }

    @GetMapping("/draft/{id}")
    @Operation(summary = "구매 요청 임시저장 목록 상세 조회", description = "임시저장된 구매 요청 목록을 상세 조회한다.")
    public PurchaseRequestDetailDto getDraftPurchaseRequestDetail(@PathVariable Long id) {
        return purchaseRequestQueryServiceImpl.getDraftDetail(id);
    }

    @GetMapping("/status")
    @Operation(summary = "구매 요청 목록을 구매 상태로 조회",
            description = "구매 요청 목록을 구매 상태: [요청 취소] [요청 대기] [검토 중] [승인] [반려]로 조회한다. ")
    public Page<PurchaseRequestSimpleDto> getRequestsByStatus(
            @RequestParam("status") PurchaseRequestStatus status,
            Pageable pageable
    ) {
        return purchaseRequestQueryServiceImpl.getRequestsByStatus(status, pageable);
    }

    @GetMapping("/search/code")
    @Operation(summary = "구매 요청 목록을 구매 요청 코드로 조회", description = "구매요청 목록을 구매 요청 코드로 조회한다.")
    public Page<PurchaseRequestSimpleDto> getRequestsByCode(
            @RequestParam String code,
            Pageable pageable
    ) {
        return purchaseRequestQueryServiceImpl.getRequestsByCode(code, pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "구매 요청 목록을 구매 요청 명으로 조회", description = "구매요청 목록을 구매 요청 명으로 조회한다.")
    public Page<PurchaseRequestSimpleDto> searchByTitle(
            @RequestParam("title") String title,
            Pageable pageable
    ) {
        return purchaseRequestQueryServiceImpl.searchByTitle(title, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "구매 요청 상세 조회", description = "구매 요청 목록 상세 조회한다.")
    public PurchaseRequestDetailDto getDetail(@PathVariable Long id) {
        return purchaseRequestQueryServiceImpl.getDetail(id);
    }

    @GetMapping
    @Operation(summary = "구매 요청 전체 목록 조회", description = "임시저장을 제외한 모든 상태의 구매 요청을 조회한다.")
    public Page<PurchaseRequestSimpleDto> getAllRequests(Pageable pageable) {
        return purchaseRequestQueryServiceImpl.getAllRequests(pageable);
    }
}