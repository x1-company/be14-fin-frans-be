package com.x1.frans.warehouse.command.domain.repository;

import com.x1.frans.warehouse.command.domain.aggregate.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    boolean existsByCode(String code);
}