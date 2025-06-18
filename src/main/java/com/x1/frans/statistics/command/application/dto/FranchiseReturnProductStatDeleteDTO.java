package com.x1.frans.statistics.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "월별 가맹점 자재별 반품량 통계를 임의로 삭제하기 위한 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseReturnProductStatDeleteDTO {

    @Schema(description = "삭제할 통계 데이터 id")
    @NotNull
    private Long id;

    @Schema(description = "삭제 사유")
    @NotNull
    @Size(max = 100)
    private String deletedReason;

}
