package com.x1.frans.returns.command.domain.vo;

import com.x1.frans.returns.enums.ProductReturnStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "반품 검토 완료")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnReviewCompleteRequestVO {

    @Schema(description = "자재 반품 상태")
    private List<ReturnDetailStatusVO> statusList;

    @Schema(description = "반품 사유")
    private String rejectReason;

}
