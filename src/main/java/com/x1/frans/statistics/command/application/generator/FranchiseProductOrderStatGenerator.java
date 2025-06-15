package com.x1.frans.statistics.command.application.generator;

import com.x1.frans.statistics.command.domain.aggregate.FranchiseProductOrderStat;
import com.x1.frans.statistics.command.domain.repository.FranchiseProductOrderStatRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class FranchiseProductOrderStatGenerator implements StatisticsGenerator {

    private final FranchiseProductOrderStatRepository franchiseProductOrderStatRepository;
    private final EntityManager em;

    @Override
    public void generate(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        log.info("[FranchiseProductOrderStatRepository] {}년 {}월 가맹점 별 자재 월별 주문량 통계 생성 시작", year, month);

        long totalQuantity = getTotalMonthlyQuantity(year, month);
        if (totalQuantity == 0) {
            return;
        }

        List<Object[]> stats = getGroupedOrderStats(year, month);

        for (Object[] row : stats) {
            FranchiseProductOrderStat stat = buildStatEntityFromRow(row, year, month, totalQuantity);
            franchiseProductOrderStatRepository.save(stat);
        }

        log.info("[FranchiseProductOrderStatRepository] {}년 {}월 가맹점 별 자재 월별 주문량 통계 생성 완료", year, month);
    }

    private long getTotalMonthlyQuantity(int year, int month) {
        String sql = """
                SELECT SUM(po.quantity)
                FROM product_order po
                JOIN orders o ON po.order_id = o.id
                WHERE YEAR(o.created_at) = :year AND MONTH(o.created_at) = :month
                """;

        Number result = (Number) em.createNativeQuery(sql)
                .setParameter("year", year)
                .setParameter("month", month)
                .getSingleResult();

        return result != null ? result.longValue() : 0L;
    }

    private List<Object[]> getGroupedOrderStats(int year, int month) {
        String sql = """
                SELECT o.franchise_id, po.product_id, SUM(po.quantity) AS order_quantity
                FROM product_order po
                JOIN orders o ON po.order_id = o.id
                WHERE YEAR(o.created_at) = :year AND MONTH(o.created_at) = :month
                GROUP BY o.franchise_id, po.product_id
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        return results;
    }

    private FranchiseProductOrderStat buildStatEntityFromRow(Object[] row, int year, int month, long totalQuantity) {
        Long franchiseId = ((Number) row[0]).longValue();
        Long productId = ((Number) row[1]).longValue();
        int orderQuantity = ((Number) row[2]).intValue();

        BigDecimal orderRatio = BigDecimal.valueOf(orderQuantity)
                .divide(BigDecimal.valueOf(totalQuantity), 4, RoundingMode.HALF_UP);

        FranchiseProductOrderStat stat = new FranchiseProductOrderStat();
        stat.setYear(year);
        stat.setMonth(month);
        stat.setFranchiseId(franchiseId);
        stat.setProductId(productId);
        stat.setOrderQuantity(orderQuantity);
        stat.setOrderRatio(orderRatio);
        stat.setCreatedAt(LocalDateTime.now());

        return stat;
    }
}