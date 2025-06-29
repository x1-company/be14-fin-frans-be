package com.x1.frans.purchaseorder.query.service;

import com.x1.frans.purchaseorder.enums.PurchaseOrderStatus;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderRequestPendingListDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseOrderQueryService {
    // 임시저장 목록 조회
    Page<PurchaseOrderSimpleDto> getDraftOrder(Pageable pageable);

    // 임시저장 상세 조회
    PurchaseOrderDetailDto getDraftOrderDetail(Long id);

    // 발주 목록 조회
    Page<PurchaseOrderSimpleDto> getOrder(Long supplierId, Pageable pageable);

    // 발주 상세 조회
    PurchaseOrderDetailDto getOrderDetail(Long id);

    // 발주 상태로 조회
    Page<PurchaseOrderSimpleDto> getOrderByStatus(PurchaseOrderStatus status, Long supplierId, Pageable pageable);

    // 발주 번호로 조회
    Page<PurchaseOrderSimpleDto> getOrderByCode(String code, Pageable pageable);

    // 발주 제목으로 조회
    Page<PurchaseOrderSimpleDto> getOrderByTitle(String title, Pageable pageable);

    List<PurchaseOrderRequestPendingListDto> getRequestPending(Long userId);
}