package com.x1.frans.product.command.domain.repository;

import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {}