package com.x1.frans.statistics.command.application.generator;

import com.x1.frans.statistics.command.domain.aggregate.FranchiseReturnProductStat;
import com.x1.frans.statistics.command.domain.repository.FranchiseReturnProductStatRepository;
import com.x1.frans.statistics.query.rawdata.dto.FranchiseReturnProductRawDTO;
import com.x1.frans.statistics.query.rawdata.service.FranchiseReturnProductRawQueryService;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class FranchiseReturnProductStatGenerator implements StatisticsGenerator {

    private final FranchiseReturnProductRawQueryService franchiseReturnProductRawQueryService;
    private final FranchiseReturnProductStatRepository franchiseReturnProductStatRepository;

    @Autowired
    public FranchiseReturnProductStatGenerator(
            FranchiseReturnProductRawQueryService franchiseReturnProductRawQueryService,
            FranchiseReturnProductStatRepository franchiseReturnProductStatRepository) {
        this.franchiseReturnProductRawQueryService = franchiseReturnProductRawQueryService;
        this.franchiseReturnProductStatRepository = franchiseReturnProductStatRepository;
    }

    public void generate(YearMonth targetMonth) {
        LocalDateTime from = targetMonth.atDay(1).atStartOfDay();
        LocalDateTime to = targetMonth.atEndOfMonth().atTime(23, 59, 59);

        log.info("[FranchiseOrderAmountStatGenerator] {}~{} 기간 가맹점 자재별 반품량 통계 생성 시작", from, to);

        List<FranchiseReturnProductRawDTO> rawData = franchiseReturnProductRawQueryService.getReturnProducts(from, to);
        int year = targetMonth.getYear();
        int month = targetMonth.getMonthValue();
        LocalDateTime now = LocalDateTime.now();

        for (FranchiseReturnProductRawDTO dto : rawData) {
            FranchiseReturnProductStat stat = FranchiseReturnProductStat.builder()
                    .year(year)
                    .month(month)
                    .returnQuantity(dto.getTotalQuantity())
                    .createdAt(now)
                    .updatedAt(now)
                    .isDeleted(false)
                    .deletedReason(null)
                    .deletedAt(null)
                    .productId(dto.getProductId())
                    .franchiseId(dto.getFranchiseId())
                    .build();

            franchiseReturnProductStatRepository.save(stat);
        }

        log.info("[FranchiseReturnProductStatGenerator] {}월 가맹점 자재별 반품량 통계 {}건 저장 완료"
                , targetMonth, rawData != null ? rawData.size() : 0);
    }

}
