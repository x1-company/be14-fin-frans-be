package com.x1.frans.approval.command.application.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalCommandRepository extends JpaRepository<ApprovalEntity,Long> {
}
