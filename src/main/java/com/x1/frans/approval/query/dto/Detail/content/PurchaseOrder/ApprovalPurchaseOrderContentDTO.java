package com.x1.frans.approval.query.dto.Detail.content.PurchaseOrder;

import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "결재 상세 본문조회 DTO - 반품")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPurchaseOrderContentDTO extends ApprovalContentDTO {

    @Schema(description = "결재 ID")
    private Long approvalId;

    @Schema(description = "문서번호")
    private String code;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String remarks;

    private String categoryType;

    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "기안일자")
    private LocalDateTime createdAt;

    @Schema(description = "결재완료 일자")
    private LocalDateTime processedAt;

    @Schema(description = "기안자 ID")
    private String drafterId;

    @Schema(description = "기안자명")
    private String drafterName;

    @Schema(description = "기안자 부서")
    private String deptName;

    @Schema(description = "결재선 사용자명")
    private String userName;

    @Schema(description = "사용자 유형")
    private String userType;

    @Schema(description = "결재 내역")
    private List<ApprovalPurchaseOrderHistoryDTO> history;

    @Schema(description = "첨부파일")
    private List<ApprovalFileDTO> files;

    @Schema(description = "총 금액")
    private BigDecimal totalAmount;

}
