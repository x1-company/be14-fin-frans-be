package com.x1.frans.supplier.command.domain.repository;

import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.user.command.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCommandRepository extends JpaRepository<SupplierEntity, Long> {
}
