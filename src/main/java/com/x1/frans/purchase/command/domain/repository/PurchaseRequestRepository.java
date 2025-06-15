package com.x1.frans.purchase.command.domain.repository;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequestEntity, Long> {
    Optional<PurchaseRequestEntity> findTopByOrderByIdDesc();
}