package com.x1.frans.returns.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "주문별 자재 목록 조회")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDTO {

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "자재 ID")
    private Integer productId;

    @Schema(description = "주문 ID")
    private Integer orderId;

    @Schema(description = "주문 수량")
    private Integer quantity;

    @Schema(description = "주문서 코드")
    private String orderCode;

    @Schema(description = "자재 코드")
    private String productCode;

    @Schema(description = "자재 명")
    private String productName;

    @Schema(description = "자재의 판매 단가 (본사-> 가맹점 공급가)")
    private BigDecimal salePrice;

    @Schema(description = "자재 단위")
    private String unit;

    @Schema(description = "자재 규격")
    private String spec;

    @Schema(description = "자재 속성 명")
    private String productAttributeName;

    @Schema(description = "자재 분류 명")
    private String productGroupName;

    @Schema(description = "자재 구분 명")
    private String productTypeName;
}
