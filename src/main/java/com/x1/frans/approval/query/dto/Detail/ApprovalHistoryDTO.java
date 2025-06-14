package com.x1.frans.approval.query.dto.Detail;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "결재이력 조회 DTO")
public interface ApprovalHistoryDTO {

    @Schema(description = "결재 이력 ID")
    Long getHistoryId();

    @Schema(description = "자재명")
    String getProductName();

    @Schema(description = "규격")
    String getSpec();

    @Schema(description = "단위")
    String getPurchaseUnit();

    @Schema(description = "수량")
    Integer getQuantity();

    // 추후 개발할 것
//    @Schema(description = "총 금액")
//    BigDecimal getTotalAmount();
}
