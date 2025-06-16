package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApprovalTemplateLineDTO {

    @Schema(name = "결재선 사용자")
    private Long userId;

    @Schema(name = "결재 유형")
    private String type;

    @Schema(name = "순서")
    private Integer seq;
}
