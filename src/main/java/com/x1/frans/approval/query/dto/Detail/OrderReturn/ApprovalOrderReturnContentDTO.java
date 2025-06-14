package com.x1.frans.approval.query.dto.Detail.OrderReturn;


import com.x1.frans.approval.query.dto.Detail.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.Detail.ApprovalFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalOrderReturnContentDTO implements ApprovalContentDTO {

    @Schema(description = "결재 ID")
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

    @Schema(description = "결재선 사용자명")
    private String userName;

    @Schema(description = "사용자 유형")
    private String userType;

    @Schema(description = "결재 내역")
    private List<ApprovalOrderReturnHistoryDTO> history;

    @Schema(description = "첨부파일")
    private List<ApprovalFileDTO> files;


}
