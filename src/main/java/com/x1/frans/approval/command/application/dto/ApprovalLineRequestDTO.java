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
    @NotNull(message = "결재자 ID는 필수입니다.")
    private Long userId;

    @Schema(description = "결재 순서")
    @NotNull(message = "결재 순서는 필수입니다.")
    private int seq;

    @Schema(description = "결재 유형 (결재, 협조, 참조, 수신)")
    @NotNull(message = "결재 유형은 필수입니다.")
    private String type;

}
