package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.PurchaseOrderApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderApprovalCommandRepository extends JpaRepository<PurchaseOrderApprovalEntity, Long> {
}
