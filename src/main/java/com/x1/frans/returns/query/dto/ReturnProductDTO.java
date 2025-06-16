package com.x1.frans.returns.query.dto;

import com.x1.frans.returns.enums.ProductReturnStatus;
import com.x1.frans.returns.enums.ReturnReasonType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "반품 상품 통합 정보")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnProductDTO {

    // 반품 상세 정보
    @Schema(description = "반품 상세 ID")
    private Long returnDetailId;

    @Schema(description = "반품 상태")
    private ProductReturnStatus returnStatus;

    @Schema(description = "반품 타입")
    private ReturnReasonType returnType;

    @Schema(description = "반품 수량")
    private Integer returnQuantity;

    @Schema(description = "주문 ID")
    private Long orderId;

    // 자재 정보
    @Schema(description = "자재 ID")
    private Long productId;

    @Schema(description = "자재 코드")
    private String productCode;

    @Schema(description = "자재명")
    private String productName;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "구매 단위")
    private String purchaseUnit;

    @Schema(description = "재고 단위")
    private String unit;

    @Schema(description = "자재 분류 ID")
    private Long productGroupId;

    @Schema(description = "자재 구분 ID")
    private Long productTypeId;

    @Schema(description = "자재 속성 ID")
    private Long productAttributeId;

    @Schema(description = "판매 단가")
    private BigDecimal salePrice;
}
