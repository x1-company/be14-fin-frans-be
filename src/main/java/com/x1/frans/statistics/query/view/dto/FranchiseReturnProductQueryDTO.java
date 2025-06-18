package com.x1.frans.statistics.query.view.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class FranchiseReturnProductQueryDTO {

    @Schema(description = "가맹점 자재별 월 반품량 통계 id")
    private Long id;

    @Schema(description = "연도")
    private Integer year;

    @Schema(description = "월")
    private Integer month;

    @Schema(description = "주문 금액")
    private Integer returnQuantity;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "수정일")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부")
    private Boolean isDeleted;

    @Schema(description = "삭제 사유")
    private String deletedReason;

    @Schema(description = "삭제일")
    private LocalDateTime deletedAt;

    @Schema(description = "자재 id")
    private Long productId;

    @Schema(description = "가맹점 id")
    private Long franchiseId;
}
