package com.x1.frans.order.command.domain.repository;

import com.x1.frans.order.command.domain.aggregate.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {


}
