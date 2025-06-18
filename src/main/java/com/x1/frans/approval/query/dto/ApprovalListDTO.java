package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "결재 리스트 조회 DTO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalListDTO {

    @Schema(description = "결재 id")
    private Long approvalId;

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
    private String deptName;

    @Schema(description = "기안자 직급")
    private String positionName;

    @Schema(description = "결재선 정보")
    private List<ApprovalListLineDTO> lines;

}
