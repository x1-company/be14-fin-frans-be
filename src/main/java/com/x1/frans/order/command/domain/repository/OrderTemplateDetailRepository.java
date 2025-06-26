package com.x1.frans.order.command.domain.repository;

import com.x1.frans.order.command.domain.aggregate.OrderTemplateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTemplateDetailRepository extends JpaRepository<OrderTemplateDetail, Long> {
}
