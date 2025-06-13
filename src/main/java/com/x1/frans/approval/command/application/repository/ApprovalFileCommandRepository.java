package com.x1.frans.approval.command.application.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalFileCommandRepository extends JpaRepository<ApprovalFileEntity,Long> {
}
