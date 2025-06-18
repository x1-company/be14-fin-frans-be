package com.x1.frans.supplier.command.domain.repository;

import com.x1.frans.supplier.command.domain.aggregate.SupplierDeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDeliveryInfoCommandRepository extends JpaRepository<SupplierDeliveryInfo, Long> {
}
