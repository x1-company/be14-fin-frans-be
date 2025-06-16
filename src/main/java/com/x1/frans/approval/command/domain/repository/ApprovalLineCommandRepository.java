package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ApprovalLineCommandRepository extends JpaRepository<ApprovalLineEntity,Long> {
    Optional<ApprovalLineEntity> findByApprovalIdAndUserId(Long id, long userId);

    Optional<ApprovalLineEntity> findByApprovalIdAndSeq(Long id, int nextSeq);

    @Modifying
    @Transactional
    @Query("DELETE FROM ApprovalLineEntity al WHERE al.approval.id = :approvalId")
    void deleteByApprovalId(@Param("approvalId") Long approvalId);
}
