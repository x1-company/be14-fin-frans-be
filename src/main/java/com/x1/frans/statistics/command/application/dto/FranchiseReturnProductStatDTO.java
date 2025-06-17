package com.x1.frans.statistics.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "관리자가 임의로 통계 데이터를 생성하기 위한 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseReturnProductStatDTO {

    @Schema(description = "연도")
    @NotNull
    private Integer year;

    @Schema(description = "월")
    @NotNull
    private Integer month;

    @Schema(description = "반품량")
    @NotNull
    private Integer returnQuantity;

    @Schema(description = "자재 id")
    @NotNull
    private Long productId;

    @Schema(description = "가맹점 id")
    @NotNull
    private Long franchiseId;

}
