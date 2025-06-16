package com.x1.frans.returns.query.dto;

import com.x1.frans.returns.enums.ReturnStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "반품 목록 조회")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnListDTO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "반품 코드")
    private String code;

    @Schema(description = "자재 개수 요약")
    private String productSummary;

    @Schema(description = "반품 상태")
    private ReturnStatus status;

    @Schema(description = "반품 요청일")
    private LocalDateTime createdAt;

    @Schema(description = "총 반품 금액")
    private BigDecimal totalAmount;

    @Schema(description = "가맹점 명")
    private String franchiseName;
}
