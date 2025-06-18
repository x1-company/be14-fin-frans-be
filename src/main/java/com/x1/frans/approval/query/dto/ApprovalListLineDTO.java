package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApprovalListLineDTO {
    @Schema(description = "결재자명")
    private String approverName;

    @Schema(description = "결재자 부서")
    private String deptName;

    @Schema(description = "결재자 직급")
    private String positionName;

    @Schema(description = "결재선 순서")
    private Integer seq;

    @Schema(description = "결재 타입(결재, 협조, 참조, 수신)")
    private String type;

    @Schema(description = "결재자 결재 상태(승인, 반려, 결재대기)")
    private String status;

}
