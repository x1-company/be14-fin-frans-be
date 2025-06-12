package com.x1.frans.statistics.query.view.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseOrderAmountQueryDTO {

    @Schema(description = "가맹점별 월 주문 금액 통계 id")
    private Long id;

    @Schema(description = "연도")
    private Integer year;

    @Schema(description = "월")
    private Integer month;

    @Schema(description = "주문 금액")
    private BigDecimal orderAmount;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "가맹점 id")
    private Long franchiseId;

}
