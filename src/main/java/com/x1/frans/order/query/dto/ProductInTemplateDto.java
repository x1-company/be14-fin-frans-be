package com.x1.frans.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductInTemplateDto {

    @Schema(description = "자재 코드")
    private String code;

    @Schema(description = "자재명")
    private String name;

    @Schema(description = "구매 단가")
    private BigDecimal salePrice;

    @Schema(description = "재고 단위")
    private String purchaseUnit;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "공급처명")
    private String supplierName;

    @Schema(description = "주문 수량")
    private Integer quantity;
}
