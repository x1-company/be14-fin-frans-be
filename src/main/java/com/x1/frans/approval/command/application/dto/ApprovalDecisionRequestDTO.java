package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApprovalDecisionRequestDTO {

    @Schema(description = "결재타입")
    private String approvalType;

    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "의견")
    private String opinion;

}
