package com.x1.frans.order.command.domain.repository;

import com.x1.frans.order.command.domain.aggregate.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
