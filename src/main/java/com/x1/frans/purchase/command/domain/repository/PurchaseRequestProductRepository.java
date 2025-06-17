package com.x1.frans.purchase.command.domain.repository;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRequestProductRepository extends JpaRepository<PurchaseRequestProductEntity, Long> {
    void deleteByPurchaseRequest(PurchaseRequestEntity entity);
    List<PurchaseRequestProductEntity> findByPurchaseRequest(PurchaseRequestEntity purchaseRequest);
}