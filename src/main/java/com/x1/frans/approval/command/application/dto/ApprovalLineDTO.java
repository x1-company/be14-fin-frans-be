package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalLineDTO {

    @Schema(description = "결재 id")
    private Long approval_id;

    @Schema(description = "결재자 id")
    private Long user_id;

    @Schema(description = "순서")
    private Long seq;

    @Schema(description = "결재타입")
    private String approvalType;

    @Schema(description = "열람 여부")
    private Boolean is_checked;

    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "반려 사유 또는 승인 의견")
    private String opnion;






}
