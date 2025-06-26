package com.x1.frans.statistics.command.application.scheduler;

import com.x1.frans.statistics.command.application.generator.StatisticsGenerator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Slf4j
@Component
public class StatisticsScheduler {

    private final List<StatisticsGenerator> statisticsGenerators;

    @Autowired
    public StatisticsScheduler(List<StatisticsGenerator> statisticsGenerators) {
        this.statisticsGenerators = statisticsGenerators;
    }

    @Scheduled(cron = "0 51 3 25 * *")
    public void generateLastMonthStats() {
        YearMonth targetMonth = YearMonth.now().minusMonths(1);

        log.info("[Scheduler] {}월 통계 생성 시작", targetMonth);

        for (StatisticsGenerator generator : statisticsGenerators) {
            try {
                generator.generate(targetMonth);
            } catch (Exception e) {
                log.error("[Scheduler] {}월 통계 생성 실패: {}", targetMonth, generator.getClass().getSimpleName(), e);
            }
        }

        log.info("[Scheduler] {}월 통계 생성 완료", targetMonth);
    }

}
