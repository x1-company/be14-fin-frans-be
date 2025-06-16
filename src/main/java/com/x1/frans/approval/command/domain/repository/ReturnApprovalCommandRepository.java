package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ReturnApprovalEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReturnApprovalCommandRepository extends JpaRepository<ReturnApprovalEntity, Long> {


    @Modifying
    @Transactional
    @Query("DELETE FROM ReturnApprovalEntity ra WHERE ra.approval.id = :approvalId")
    void deleteByApprovalId(@Param("approvalId") Long approvalId);
}
