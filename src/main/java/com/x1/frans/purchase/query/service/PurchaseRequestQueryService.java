package com.x1.frans.purchase.query.service;

import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseRequestQueryService {

    Page<PurchaseRequestSimpleDto> getRequestsByStatus(PurchaseRequestStatus status, Pageable pageable);

    Page<PurchaseRequestSimpleDto> searchByTitle(String title, Pageable pageable);

    Page<PurchaseRequestSimpleDto> getRequestsByCode(String code, Pageable pageable);

    PurchaseRequestDetailDto getDetail(Long id);

    Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable);

    PurchaseRequestDetailDto getDraftDetail(Long id);

    Page<PurchaseRequestSimpleDto> getAllRequests(Pageable pageable);
}