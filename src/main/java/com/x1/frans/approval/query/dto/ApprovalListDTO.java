package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "결재 리스트 조회 DTO")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalListDTO {

    @Schema(description = "결재 id")
    private Long id;

    @Schema(description = "결재 코드")
    private String code;

    @Schema(description = "결재 제목")
    private String title;

    @Schema(description = "결재 상태(결재완료, 결재반려, 결재중,임시저장)")
    private String status;

    @Schema(description = "기안일시")
    private LocalDateTime createdAt;

    @Schema(description = "요청여부 (임시저장)")
    private Boolean isRequested;

    @Schema(description = "기안자명")
    private String drafterName;

    @Schema(description = "기안자 부서")
    private String drafterDeptName;

    @Schema(description = "기안자 직급")
    private String drafterPositionName;

    @Schema(description = "결재자명")
    private String approverName;

    @Schema(description = "결재자 부서")
    private String approvalDeptName;

    @Schema(description = "결재자 직급")
    private String approvalPositionName;

    @Schema(description = "결재선 순서")
    private Integer seq;

    @Schema(description = "결재 타입(결재, 협조, 참조, 수신)")
    private String approvalType;

    @Schema(description = "결재자 결재 상태(승인, 반려, 결재대기)")
    private String approvalLineStatus;

}
