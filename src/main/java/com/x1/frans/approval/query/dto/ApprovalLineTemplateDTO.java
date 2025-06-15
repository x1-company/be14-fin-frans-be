package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApprovalLineTemplateDTO {

    @Schema(description = "템플릿 ID")
    private Long id;

    @Schema(description = "템플릿명")
    private String name;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "순서")
    private Integer seq;

}
