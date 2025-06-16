package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.PurchaseOrderApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseOrderApprovalCommandRepository extends JpaRepository<PurchaseOrderApprovalEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM PurchaseOrderApprovalEntity poa WHERE poa.approval.id = :approvalId")
    void deleteByApprovalId(@Param("approvalId") Long approvalId);
    void deleteByApprovalId(long approvalId);
}
