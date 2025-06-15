package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApprovalDecisionRequestDTO {

    @Schema(description = "결재타입")
    @NotNull(message = "결재 타입은 필수입니다.")
    private String approvalType;

    @Schema(description = "결재 상태")
    @NotNull(message = "결재 상태는 필수입니다.")
    private String status;

    @Schema(description = "의견")
    private String opinion;

}
