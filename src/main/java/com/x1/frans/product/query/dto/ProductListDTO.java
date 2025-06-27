package com.x1.frans.product.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "자재 목록 조회")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductListDTO {

    @Schema(description = "자재 id")
    private Long id;

    @Schema(description = "자재 코드")
    private String code;

    @Schema(description = "자재명")
    private String name;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "구매 단위")
    private String purchaseUnit;

    @Schema(description = "재고 단위")
    private String unit;

    @Schema(description = "자재 분류")
    private Long productGroupId;

    @Schema(description = "자재 구분")
    private Long productTypeId;

    @Schema(description = "자재 속성")
    private Long productAttributeId;

    @Schema(description = "자재 사용 여부")
    private Boolean isActive;

    @Schema(description = "공급처명")
    private String supplierName;

}
