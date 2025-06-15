package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.OrderApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalCommandRepository extends JpaRepository<OrderApprovalEntity, Long> {
}
