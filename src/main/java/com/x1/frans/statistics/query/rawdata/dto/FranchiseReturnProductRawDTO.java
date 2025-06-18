package com.x1.frans.statistics.query.rawdata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FranchiseReturnProductRawDTO {

    @Schema(description = "자재 id")
    private Long productId;

    @Schema(description = "가맹점 id")
    private Long franchiseId;

    @Schema(description = "반품량")
    private Integer totalQuantity;
}
