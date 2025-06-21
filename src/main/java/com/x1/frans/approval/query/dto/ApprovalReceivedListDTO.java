package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "수신받은 문서 목록 조회 DTO - 진행중, 결재완료, 결재반려")
@Getter
@Setter
public class ApprovalReceivedListDTO {

    @Schema(description = "문서 ID")
    private Long id;

    @Schema(description = "문서 제목")
    private String title;

    @Schema(description = "기안자명")
    private String name;

    @Schema(description = "기안자 부서")
    private String departmentName;

    @Schema(description = "기안자 직급")
    private String positionName;

    @Schema(description = "작성일자")
    private LocalDateTime createdAt;


}
