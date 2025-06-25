package com.x1.frans.approval.query.dto.Detail.content.PurchaseOrder;

import com.x1.frans.approval.query.dto.Detail.content.ApprovalHistoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Schema(description = "결재이력 조회 DTO - 발주")

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPurchaseOrderHistoryDTO implements ApprovalHistoryDTO {

    @Schema(description = "결재 이력 ID")
    private Long historyId;

    @Schema(description = "자재번호")
    private String code;

    @Schema(description = "자재명")
    private String productName;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "단위")
    private String purchaseUnit;

    @Schema(description = "수량")
    private Integer quantity;

    @Schema(description = "단가")
    private BigDecimal purchasePrice;

    @Schema(description = "금액")
    private BigDecimal amount;


}
