package com.x1.frans.purchaseorder.command.domain.repository;

import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {
    Optional<PurchaseOrderEntity> findTopByOrderByIdDesc();
}