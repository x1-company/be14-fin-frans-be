package com.x1.frans.statistics.query.rawdata.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class FranchiseOrderAmountRawDTO {

    @Schema(description = "가맹점 id")
    private Long franchiseId;

    @Schema(description = "주문 금액")
    private BigDecimal totalAmount;

}
