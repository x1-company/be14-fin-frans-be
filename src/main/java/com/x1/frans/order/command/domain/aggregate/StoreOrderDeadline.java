package com.x1.frans.order.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "store_order_deadline")
public class StoreOrderDeadline {

    /**
     * 단 하나의 레코드만 존재 (id = 1). 주문 마감 시간을 관리하기 위한 단일 설정값 용도.
     */
    @Id
    private Long id;

    @Column(name = "order_deadline_at", nullable = false)
    private LocalTime orderDeadlineAt;

    public StoreOrderDeadline(LocalTime orderDeadlineAt) {
        this.id = 1L;
        this.orderDeadlineAt = orderDeadlineAt;
    }

    public void updateDeadlineTime(LocalTime newDeadlineTime) {
        this.orderDeadlineAt = newDeadlineTime;
    }
}