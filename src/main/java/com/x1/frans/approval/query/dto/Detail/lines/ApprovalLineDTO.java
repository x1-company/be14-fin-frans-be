package com.x1.frans.approval.query.dto.Detail.lines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Getter
public class ApprovalLineDTO {

    @Schema(description = "결재선 ID")
    private Long approvalLineId;

    @Schema(description = "결재자 ID")
    private Long id;

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

    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "결재 처리 시간")
    private LocalDateTime processedAt;

    @Schema(description = "열람 여부")
    private Boolean isChecked;

    @Schema(description = "열람 시간")
    private LocalDateTime checkedAt;

    @Schema(description = "의견")
    private String opinion;

    private String url;
}
