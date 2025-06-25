package com.x1.frans.approval.query.dto.Detail.content.OrderReturn;

import com.x1.frans.approval.query.dto.Detail.content.ApprovalHistoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "결재이력 조회 DTO - 주문, 반품")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalOrderReturnHistoryDTO implements ApprovalHistoryDTO {

    @Schema(description = "결재 이력 ID")
    private Long historyId;

    @Schema(description = "자재명")
    private String productName;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "단위")
    private String purchaseUnit;

    @Schema(description = "수량")
    private Integer quantity;

    @Schema(description = "단가")
    private BigDecimal salePrice;

    @Schema(description = "금액")
    private BigDecimal amount;

}
