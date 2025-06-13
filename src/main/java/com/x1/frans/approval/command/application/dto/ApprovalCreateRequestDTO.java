package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Schema(description = "결재 등록 요청 DTO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalCreateRequestDTO {

    @Schema(description = "결재 제목")
    @NotNull(message = "결재 제목은 필수입니다.")
    private String title;

    @Schema(description = "결재 내용")
    private String remarks;

    @Schema(description = "요청여부(임시저장)")
    @NotNull(message = "요청여부는 필수입니다.")
    private Boolean isRequest;

    @Schema(description = "결재 유형 ID ( 주문, 반품, 발주 )")
    @NotNull(message = "결재 유형 ID는 필수입니다.")
    private Long approvalCategoryId;  // 또는 enum ID 등

    @Schema(description = "결재선 정보")
    @NotNull(message = "결재선 정보는 필수입니다.")
    private List<ApprovalLineRequestDTO> approvalLines;

    @Schema(description = "첨부파일 목록")
    private List<ApprovalFileDTO> files;

}

