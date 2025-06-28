package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "진행중인 주문 결재 건수 조회")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseApprovalCountDTO {

    @Schema(description = "진행중 주문 결재 개수")
    private int orderApprovalCount;

    @Schema(description = "진행중 반품 결재 개수")
    private int returnApprovalCount;

}
