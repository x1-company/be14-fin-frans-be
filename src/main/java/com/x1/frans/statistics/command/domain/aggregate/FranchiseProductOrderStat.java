package com.x1.frans.statistics.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 가맹점 별 월별 자재 주문량 통계
@Entity
@Table(name = "franchise_product_order_stat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseProductOrderStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer orderQuantity;

    @Column(nullable = false)
    private BigDecimal orderRatio;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long franchiseId;
}
