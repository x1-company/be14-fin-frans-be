package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApprovalLineTemplateDetailDTO {

    @Schema(description = "결재선 템플릿 ID")
    private Long templateId;

    @Schema(description = "결재자명")
    private String userName;

    @Schema(description = "결재 타입")
    private String type;

    @Schema(description = "결재자 부서")
    private String departmentName;

    @Schema(description = "결재자 직급")
    private String positionName;

    @Schema(description = "순서")
    private Integer seq;


}
