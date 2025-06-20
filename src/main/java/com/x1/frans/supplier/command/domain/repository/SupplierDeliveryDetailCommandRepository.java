package com.x1.frans.supplier.command.domain.repository;

import com.x1.frans.supplier.command.domain.aggregate.SupplierDeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDeliveryDetailCommandRepository extends JpaRepository<SupplierDeliveryDetail, Long> {
}
