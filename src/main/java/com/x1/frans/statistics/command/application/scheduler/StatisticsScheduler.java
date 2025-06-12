package com.x1.frans.statistics.command.application.scheduler;

import com.x1.frans.statistics.command.application.generator.FranchiseOrderAmountStatGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Slf4j
@Component
public class StatisticsScheduler {

    private final FranchiseOrderAmountStatGenerator franchiseOrderAmountStatGenerator;

    @Autowired
    public StatisticsScheduler(FranchiseOrderAmountStatGenerator franchiseOrderAmountStatGenerator) {
        this.franchiseOrderAmountStatGenerator = franchiseOrderAmountStatGenerator;
    }

    @Scheduled(cron = "0 28 15 12 * *")
    public void generateLastMonthStats() {
        YearMonth targetMonth = YearMonth.now().minusMonths(1);

        log.info("[Scheduler] {}월 가맹점 주문 금액 통계 생성 시작", targetMonth);
        try {
            franchiseOrderAmountStatGenerator.generate(targetMonth); // ✅ YearMonth만 넘김
            log.info("[Scheduler] {}월 통계 생성 완료", targetMonth);
        } catch (Exception e) {
            log.error("[Scheduler] {}월 통계 생성 실패", targetMonth, e);
        }
    }

}
