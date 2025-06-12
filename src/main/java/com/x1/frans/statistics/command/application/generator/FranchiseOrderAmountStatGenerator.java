package com.x1.frans.statistics.command.application.generator;

import com.x1.frans.statistics.command.domain.aggregate.FranchiseOrderAmountStat;
import com.x1.frans.statistics.command.domain.repository.FranchiseOrderAmountStatRepository;
import com.x1.frans.statistics.query.rawdata.dto.FranchiseOrderAmountRawDTO;
import com.x1.frans.statistics.query.rawdata.service.FranchiseOrderAmountRawQueryService;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FranchiseOrderAmountStatGenerator {

    private final FranchiseOrderAmountRawQueryService rawQueryService;
    private final FranchiseOrderAmountStatRepository statRepository;

    @Autowired
    public FranchiseOrderAmountStatGenerator(FranchiseOrderAmountRawQueryService rawQueryService,
                                             FranchiseOrderAmountStatRepository statRepository) {
        this.rawQueryService = rawQueryService;
        this.statRepository = statRepository;
    }

    public void generate(YearMonth targetMonth) {
        LocalDateTime from = targetMonth.atDay(1).atStartOfDay();
        LocalDateTime to = targetMonth.atEndOfMonth().atTime(23, 59, 59);

        log.info("[FranchiseOrderAmountStatGenerator] {}~{} 기간 통계 생성 시작", from, to);

        List<FranchiseOrderAmountRawDTO> rawData = rawQueryService.getOrderAmounts(from, to);
        int year = targetMonth.getYear();
        int month = targetMonth.getMonthValue();
        LocalDateTime createdAt = LocalDateTime.now();

        for (FranchiseOrderAmountRawDTO dto : rawData) {
            FranchiseOrderAmountStat stat = FranchiseOrderAmountStat.builder()
                    .year(year)
                    .month(month)
                    .orderAmount(dto.getTotalAmount())
                    .createdAt(createdAt)
                    .franchiseId(dto.getFranchiseId())
                    .build();

            statRepository.save(stat);
        }

        log.info("[FranchiseOrderAmountStatGenerator] {}월 통계 {}건 저장 완료"
                    , targetMonth, rawData != null ? rawData.size() : 0);
    }

}
