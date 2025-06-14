package com.x1.frans.approval.query.dto.Detail;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "결재 상세 본문조회 DTO")
public interface ApprovalContentDTO {

    @Schema(description = "결재 상세조회 ID")
    Long getApprovalId();

    @Schema(description = "문서번호")
    String getCode();

    @Schema(description = "제목")
    String getTitle();

    @Schema(description = "내용")
    String getRemarks();

    @Schema(description = "결재 상태")
    String getStatus();

    @Schema(description = "기안일자")
    LocalDateTime getCreatedAt();

    @Schema(description = "기안자명")
    String getDrafterName();

    @Schema(description = "사용자명")
    String getUserName();

    @Schema(description = "사용자 유형")
    String getUserType();

    @Schema(description = "첨부파일")
    List<ApprovalFileDTO> getFiles();

}
