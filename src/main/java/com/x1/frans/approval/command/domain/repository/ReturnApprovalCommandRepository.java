package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ReturnApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnApprovalCommandRepository extends JpaRepository<ReturnApprovalEntity, Long> {
}
