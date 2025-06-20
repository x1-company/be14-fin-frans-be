package com.x1.frans.returns.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "검토 완료된 반품 목록 조회 DTO")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HqReturnReviewCompltedDTO {

    @Schema(description = "반품 코드")
    private String code;

    @Schema(description = "상태")
    private String status;

    @Schema(description = "작성 일자")
    private LocalDateTime createdAt;

    @Schema(description = "총 금액")
    private BigDecimal totalAmount;

    @Schema(description = "반품 사유")
    private String description;
}
