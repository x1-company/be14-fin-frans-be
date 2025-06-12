package com.x1.frans.warehouse.command.domain.repository;

import com.x1.frans.warehouse.command.domain.aggregate.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {

    Optional<WarehouseEntity> findTopByOrderByIdDesc();
}