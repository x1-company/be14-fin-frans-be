package com.x1.frans.purchaseorder.query.controller;

import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDraftListDto;
import com.x1.frans.purchaseorder.query.service.PurchaseOrderQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hq/purchaseorder")
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
}
