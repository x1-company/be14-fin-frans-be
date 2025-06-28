package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "완료된 주문 결재 건수 조회")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseApprovedApprovalCountDTO {

    @Schema(description = "이번 달 주문 결재 완료 건수")
    private int orderThisMonthCount;

    @Schema(description = "지난 달 주문 결재 완료 건수")
    private int orderLastMonthCount;

}
