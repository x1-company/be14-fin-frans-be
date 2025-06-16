package com.x1.frans.purchase.command.domain.repository;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequestEntity, Long> {
    Optional<PurchaseRequestEntity> findTopByOrderByIdDesc();
    Page<PurchaseRequestEntity> findAllByStatus(PurchaseRequestStatus status, Pageable pageable);
    Optional<PurchaseRequestEntity> findByIdAndStatus(Long id, PurchaseRequestStatus status);
}