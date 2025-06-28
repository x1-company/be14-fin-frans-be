package com.x1.frans.returns.command.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "반품 반려 사유")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnRejectRequestVO {

    @Schema(description = "반품 반려 사유")
    private String rejectReason;
}
