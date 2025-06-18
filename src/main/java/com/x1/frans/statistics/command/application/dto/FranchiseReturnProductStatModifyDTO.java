package com.x1.frans.statistics.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class FranchiseReturnProductStatModifyDTO {

    @Schema(description = "수정할 통계 id")
    @NotNull
    private Long id;

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
