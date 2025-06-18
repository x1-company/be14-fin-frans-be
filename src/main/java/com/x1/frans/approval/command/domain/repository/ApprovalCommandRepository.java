package com.x1.frans.approval.command.domain.repository;

import com.x1.frans.approval.command.domain.aggregate.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApprovalCommandRepository extends JpaRepository<ApprovalEntity,Long> {
    @Query("SELECT COALESCE(MAX(a.degree), 0) FROM ApprovalEntity a WHERE a.code = :code")
    int findMaxDegreeByCode(@Param("code") String code);
}
