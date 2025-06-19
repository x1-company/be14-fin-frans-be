package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalLineTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ApprovalLineTemplateCommandRepository extends JpaRepository<ApprovalLineTemplateEntity, Long> {

    @Query("SELECT COALESCE(MAX(t.seq), 0) FROM ApprovalLineTemplateEntity t")
    BigDecimal findMaxSeq();

    List<ApprovalLineTemplateEntity> findByUserIdOrderBySeqDesc(Long userId);
}
