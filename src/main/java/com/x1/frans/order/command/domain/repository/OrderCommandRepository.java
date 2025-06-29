package com.x1.frans.order.command.domain.repository;

import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderCommandRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("UPDATE Order o SET o.status = 'DELIVERING' WHERE o.id IN :orderIds")
    void updateOrderStatusToDelivering(@Param("orderIds") List<Long> orderIds);

    List<Order> findByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime before);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.code LIKE CONCAT(:codePrefix, '%')")
    int countByCodeStartingWith(@Param("codePrefix") String codePrefix);

}
