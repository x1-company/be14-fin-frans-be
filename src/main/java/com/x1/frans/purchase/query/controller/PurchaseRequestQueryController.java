package com.x1.frans.purchase.query.controller;

import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import com.x1.frans.purchase.query.service.PurchaseRequestQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/purchase/requests")
@RequiredArgsConstructor
public class PurchaseRequestQueryController {

    private final PurchaseRequestQueryService purchaseRequestQueryService;

    @GetMapping("/draft")
    public Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable) {
        return purchaseRequestQueryService.getDraftPurchaseRequests(pageable);
    }
}