package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalLineTemplateDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineTemplateDetailCommandRepository extends JpaRepository<ApprovalLineTemplateDetailEntity,Long> {


    void deleteByTemplateId(Long templateId);
}
