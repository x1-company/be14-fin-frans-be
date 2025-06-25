package com.x1.frans.purchaseorder.query.service;

import com.x1.frans.purchaseorder.enums.PurchaseOrderStatus;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderQueryService {
    // 1. 임시저장 목록 조회
    Page<PurchaseOrderSimpleDto> getDraftOrder(Pageable pageable);

    // 2. 임시저장 상세 조회
    PurchaseOrderDetailDto getDraftOrderDetail(Long id);

    // 3. 발주 목록 조회
    Page<PurchaseOrderSimpleDto> getOrder(Long supplierId, Pageable pageable);

    // 4. 발주 상세 조회
    PurchaseOrderDetailDto getOrderDetail(Long id);

    // 5. 발주 상태로 조회
    Page<PurchaseOrderSimpleDto> getOrderByStatus(PurchaseOrderStatus status, Pageable pageable);}