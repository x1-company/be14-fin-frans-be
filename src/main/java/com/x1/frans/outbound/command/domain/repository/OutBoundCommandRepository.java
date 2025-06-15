package com.x1.frans.outbound.command.domain.repository;

import com.x1.frans.outbound.command.domain.aggregate.OutboundEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OutBoundCommandRepository extends JpaRepository<OutboundEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 충돌 방지를 위한 행 잠금
    @Query(value = "SELECT o.code FROM OutboundEntity o WHERE o.code LIKE :prefix ORDER BY o.code DESC LIMIT 1")
    String findLastCodeForToday(@Param("prefix") String prefix);
}
