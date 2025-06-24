package com.x1.frans.order.command.domain.repository;

import com.x1.frans.order.command.domain.aggregate.OrderTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTemplateRepository extends JpaRepository<OrderTemplate, Long> {
}
