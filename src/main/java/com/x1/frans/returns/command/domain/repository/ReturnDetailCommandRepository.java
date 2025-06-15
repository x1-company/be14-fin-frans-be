package com.x1.frans.returns.command.domain.repository;

import com.x1.frans.returns.command.domain.aggregate.ReturnDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnDetailCommandRepository extends JpaRepository<ReturnDetailEntity, Long> {
}
