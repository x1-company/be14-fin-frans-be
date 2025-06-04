package com.x1.frans.supplier.command.repository;

import com.x1.frans.supplier.command.aggregate.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCommandRepository extends JpaRepository<SupplierEntity, Integer> {
}
