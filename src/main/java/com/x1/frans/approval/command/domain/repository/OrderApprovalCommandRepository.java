package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.OrderApprovalEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderApprovalCommandRepository extends JpaRepository<OrderApprovalEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderApprovalEntity o WHERE o.approval.id = :approvalId")
    void deleteByApprovalId(@Param("approvalId") long approvalId);
}
