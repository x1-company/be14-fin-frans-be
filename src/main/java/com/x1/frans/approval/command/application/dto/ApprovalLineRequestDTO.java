package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalLineRequestDTO {

    @Schema(description = "결재자 ID")
    private Long userId;

    @Schema(description = "결재 순서")
    private int seq;

    @Schema(description = "결재 유형 (결재, 협조, 참조, 수신)")
    private String type;

}
