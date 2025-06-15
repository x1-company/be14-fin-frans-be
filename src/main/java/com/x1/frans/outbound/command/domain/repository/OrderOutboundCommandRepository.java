package com.x1.frans.outbound.command.domain.repository;

import com.x1.frans.outbound.command.domain.aggregate.OrderOutboundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOutboundCommandRepository extends JpaRepository<OrderOutboundEntity, Long> {
}
