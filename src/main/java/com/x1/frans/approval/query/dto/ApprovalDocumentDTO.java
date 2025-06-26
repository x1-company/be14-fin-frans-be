package com.x1.frans.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDocumentDTO {

//    @Schema(description = "결재유형(ORDER, RETURN, PURCHASE_ORDER)")
//    private String categoryType;

    @Schema(description = "문서 ID")
    private Long documentId;

        @Schema(description = "문서번호")
    private String documentNo;

    @Schema(description = "가맹점명")
    private String franchiseName;

        @Schema(description = "공급처명")
    private String supplierName;

        @Schema(description = "반품사유")
    private String reason;

        @Schema(description = "작성일자")
    private String createdAt;

        @Schema(description = "금액")
    private String amount;
}
