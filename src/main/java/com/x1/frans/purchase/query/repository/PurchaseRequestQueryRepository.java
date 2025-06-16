package com.x1.frans.purchase.query.repository;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequestQueryRepository extends JpaRepository<PurchaseRequestEntity, Long> {

    Page<PurchaseRequestEntity> findByStatus(String status, Pageable pageable);

    Page<PurchaseRequestEntity> findAllByStatus(PurchaseRequestStatus status, Pageable pageable);
}