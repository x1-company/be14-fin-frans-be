package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApprovalLineTemplateModifyRequestDTO {

    @Schema(description = "결재선 템플릿명")
    @NotNull(message = "템플릿명은 필수입니다.")
    private String name;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "순서")
    @NotNull(message = "순서는 필수입니다.")
    private Integer seq;

    @Schema(description = "결재선 템플릿 정보")
    @NotNull(message = "결재선 템플릿 정보는 필수입니다.")
    private List<ApprovalTemplateLineDTO> lines;
}
