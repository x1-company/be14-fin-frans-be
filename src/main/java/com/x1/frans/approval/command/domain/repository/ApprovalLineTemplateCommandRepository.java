package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalLineTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineTemplateCommandRepository extends JpaRepository<ApprovalLineTemplateEntity, Long> {
}
