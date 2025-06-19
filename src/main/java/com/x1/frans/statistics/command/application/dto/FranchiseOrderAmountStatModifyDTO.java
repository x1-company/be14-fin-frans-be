package com.x1.frans.statistics.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "월별 가맹점 총 주문 금액 임의로 생성, 수정하기 위한 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseOrderAmountStatModifyDTO {

    @Schema(description = "수정할 통계 id")
    @NotNull
    private Long id;

    @Schema(description = "연도")
    @NotNull
    private Integer year;

    @Schema(description = "월")
    @NotNull
    private Integer month;

    @Schema(description = "총 주문 금액")
    @NotNull
    private BigDecimal orderAmount;

    @Schema(description = "가맹점 id")
    @NotNull
    private Long franchiseId;

}
