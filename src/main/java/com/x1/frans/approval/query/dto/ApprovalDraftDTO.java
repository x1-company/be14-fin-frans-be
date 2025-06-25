package com.x1.frans.approval.query.dto;

import com.x1.frans.approval.query.dto.Detail.content.ApprovalFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDraftDTO {

    @Schema(description = "결재 id")
    private Long approvalId;

    @Schema(description = "결재 코드")
    private String code;

    @Schema(description = "결재 제목")
    private String title;

    @Schema(description = "결재 내용")
    private String remarks;

    @Schema(description = "결재 상태(결재완료, 결재반려, 결재중, 임시저장)")
    private String status;

    @Schema(description = "요청여부 (임시저장)")
    private Boolean isRequested;

    @Schema(description = "기안자 ID")
    private Long id;

    private String categoryType;

    @Schema(description = "문서 유형")
    private List<ApprovalDocumentDTO> approvalDocuments;

    @Schema(description = "결재선 정보")
    private List<ApprovalDraftLineDTO> lines;

    @Schema(description = "첨부파일")
    private List<ApprovalFileDTO> files;

}
