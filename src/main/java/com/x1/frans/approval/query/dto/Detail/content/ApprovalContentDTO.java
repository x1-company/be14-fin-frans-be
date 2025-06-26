package com.x1.frans.approval.query.dto.Detail.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "결재 상세 본문조회 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApprovalContentDTO {

    @Schema(description = "결재 상세조회 ID")
    private Long approvalId;

    @Schema(description = "문서번호")
    private String code;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String remarks;

    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "기안일자")
    private LocalDateTime createdAt;

    @Schema(description = "기안자명")
    private String drafterName;

    @Schema(description = "사용자명")
    private String userName;

    @Schema(description = "사용자 유형")
    private String userType;

    @Schema(description = "첨부파일")
    private List<ApprovalFileDTO> files;

    @Schema(description = "총 금액")
    private BigDecimal totalAmount;
}
