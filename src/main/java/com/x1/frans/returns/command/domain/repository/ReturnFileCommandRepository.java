package com.x1.frans.returns.command.domain.repository;

import com.x1.frans.returns.command.domain.aggregate.ReturnFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnFileCommandRepository extends JpaRepository<ReturnFileEntity, Long> {
}
