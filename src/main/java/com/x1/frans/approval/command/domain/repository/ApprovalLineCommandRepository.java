package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalLineCommandRepository extends JpaRepository<ApprovalLineEntity,Long> {
    Optional<ApprovalLineEntity> findByApprovalIdAndUserId(Long id, long userId);

    Optional<ApprovalLineEntity> findByApprovalIdAndSeq(Long id, int nextSeq);
}
