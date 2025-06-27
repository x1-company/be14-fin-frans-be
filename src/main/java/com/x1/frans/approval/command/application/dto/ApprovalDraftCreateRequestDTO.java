package com.x1.frans.approval.command.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.x1.frans.approval.common.ApprovalCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Schema(description = "결재 임시저정 요청 DTO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalDraftCreateRequestDTO {

    private Long id;

    @Schema(description = "결재 제목")
    @NotNull(message = "결재 제목은 필수입니다.")
    private String title;

    @Schema(description = "결재 내용")
    private String remarks;

    @Schema(description = "요청여부(임시저장)")
    @NotNull(message = "요청여부는 필수입니다.")
    private Boolean isRequest;

    @Schema(description = "결재 문서 유형")
    @NotNull(message = "결재 문서 유형은 필수입니다.")
    private ApprovalCategoryType categoryType;

    @Schema(description = "결재에 포함될 문서 정보")
    private List<Long> approvalDocumentId;

    @Schema(description = "결재선 정보")
    private List<ApprovalLineRequestDTO> approvalLines;

    @Schema(description = "첨부파일 목록")
    private List<ApprovalFileDTO> files;


}
