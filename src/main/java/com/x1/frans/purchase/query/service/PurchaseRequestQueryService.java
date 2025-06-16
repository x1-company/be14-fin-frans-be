package com.x1.frans.purchase.query.service;

import com.x1.frans.exception.PurchaseRequestNotFoundException;
import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.command.domain.repository.PurchaseRequestRepository;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import com.x1.frans.purchase.query.repository.PurchaseRequestQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRequestQueryService {

    private final PurchaseRequestQueryRepository purchaseRequestQueryRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;

    @Autowired
    public PurchaseRequestQueryService(PurchaseRequestQueryRepository purchaseRequestQueryRepository, PurchaseRequestRepository purchaseRequestRepository) {
        this.purchaseRequestQueryRepository = purchaseRequestQueryRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable) {
        // status는 "임시저장" (enum 또는 상수 사용 가능)
        Page<PurchaseRequestEntity> entities = purchaseRequestRepository.findAllByStatus(PurchaseRequestStatus.DRAFT, pageable);

        return entities.map(entity -> PurchaseRequestSimpleDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .title(entity.getTitle())
                .status(entity.getStatus().getLabel())
                .requestedDeliveryDate(entity.getRequestedDeliveryDate())
                .totalAmount(entity.getTotalAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build());
    }

    public PurchaseRequestDetailDto getDraftDetail(Long id) {
        PurchaseRequestEntity entity = purchaseRequestRepository
                .findByIdAndStatus(id, PurchaseRequestStatus.DRAFT)
                .orElseThrow(() -> new PurchaseRequestNotFoundException("임시저장 구매요청을 찾을 수 없습니다."));
        return PurchaseRequestDetailDto.from(entity);
    }

    public Page<PurchaseRequestSimpleDto> getRequestsByStatus(PurchaseRequestStatus status, Pageable pageable) {
        return purchaseRequestQueryRepository.findAllByStatus(status, pageable)
                .map(PurchaseRequestSimpleDto::from);
    }

    public Page<PurchaseRequestSimpleDto> getRequestsByCode(String code, Pageable pageable) {
        return purchaseRequestRepository.findByCodeContaining(code, pageable)
                .map(PurchaseRequestSimpleDto::from);
    }
}