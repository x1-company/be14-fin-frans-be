package com.x1.frans.approval.command.application.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalLineCommandRepository extends JpaRepository<ApprovalLineEntity,String> {
}
