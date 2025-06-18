package com.x1.frans.purchaseorder.command.application.service;

import com.x1.frans.exception.*;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.command.domain.repository.PurchaseRequestRepository;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderEntity;
import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderProductEntity;
import com.x1.frans.purchaseorder.command.domain.repository.PurchaseOrderRepository;
import com.x1.frans.purchaseorder.enums.PurchaseOrderStatus;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.HqUserDetailCommandRepository;
import com.x1.frans.user.command.repository.UserCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseOrderCommandServiceImpl implements PurchaseOrderCommandService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final UserCommandRepository userCommandRepository;
    private final HqUserDetailCommandRepository hqUserDetailCommandRepository;

    @Autowired
    public PurchaseOrderCommandServiceImpl(
            PurchaseOrderRepository purchaseOrderRepository,
            ProductRepository productRepository,
            PurchaseRequestRepository purchaseRequestRepository,
            UserCommandRepository userCommandRepository,
            HqUserDetailCommandRepository hqUserDetailCommandRepository
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.userCommandRepository = userCommandRepository;
        this.hqUserDetailCommandRepository = hqUserDetailCommandRepository;
    }

    // 구매팀만 발주 가능
    private static final List<Long> ALLOWED_DEPARTMENT_IDS = List.of(1L);

    @Override
    @Transactional
    public PurchaseOrderEntity saveDraft(PurchaseOrderEntity entity, Long userId) {
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보 없음"));
        Long departmentId = hqUserDetailCommandRepository.findByUser(user)
                .orElseThrow(() -> new InvalidDepartmentException("부서 정보 없음"))
                .getDepartmentId();
        if (!ALLOWED_DEPARTMENT_IDS.contains(departmentId)) {
            throw new InvalidDepartmentException("구매팀만 발주할 수 있습니다.");
        }
        entity.setUser(user);

        entity.setCode(generateOrderCode());

        Long supplierId = entity.getSupplier().getId();
        BigDecimal total = BigDecimal.ZERO;
        int idx = 1;
        for (PurchaseOrderProductEntity pop : entity.getPurchaseOrderProducts()) {
            // 자재 정보 조회
            ProductEntity product = productRepository.findById(pop.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("자재 없음"));

            // 공급처 일치 검증
            if (!product.getSupplier().getId().equals(supplierId)) {
                throw new ProductSupplierMisMatchException("자재 공급처와 발주 공급처가 다릅니다.");
            }

            // 구매요청 상태 검증 (반드시 승인 완료여야만)
            PurchaseRequestEntity purchaseRequest = purchaseRequestRepository.findById(pop.getPurchaseRequestId())
                    .orElseThrow(() -> new PurchaseRequestNotFoundException("구매요청 없음"));
            if (purchaseRequest.getStatus() != PurchaseRequestStatus.APPROVED) {
                throw new InvalidPurchaseRequestStatusException("승인 완료된 구매요청만 발주할 수 있습니다.");
            }

            // 금액 합산 및 연관 세팅
            total = total.add(product.getPurchasePrice().multiply(BigDecimal.valueOf(pop.getQuantity())));
            pop.setNo(idx++);
            pop.setPurchaseOrder(entity);
        }

        entity.setTotalAmount(total);
        entity.setStatus(PurchaseOrderStatus.DRAFT);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setIsRequested(false);

        return purchaseOrderRepository.save(entity);
    }

    private String generateOrderCode() {
        Long maxId = purchaseOrderRepository.findTopByOrderByIdDesc().map(PurchaseOrderEntity::getId).orElse(0L);
        return String.format("PO%04d", maxId + 1);
    }
}