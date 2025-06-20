package com.x1.frans.supplier.command.domain.repository;

import com.x1.frans.supplier.command.domain.aggregate.SupplierDeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HqDeliveryInfoRepository extends JpaRepository<SupplierDeliveryInfo, Long> {
}
