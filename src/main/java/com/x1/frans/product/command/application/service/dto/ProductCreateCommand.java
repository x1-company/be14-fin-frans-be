package com.x1.frans.product.command.application.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import lombok.ToString;

@Schema(description = "자재 등록 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductCreateCommand {

    @Schema(description = "자재 코드")
    private String code;

    @Schema(description = "자재명")
    private String name;

    @Schema(description = "구매 가격")
    private BigDecimal purchasePrice;

    @Schema(description = "판매 가격")
    private BigDecimal salePrice;

    @Schema(description = "재고 단위")
    private String unit;

    @Schema(description = "구매 단위")
    private String purchaseUnit;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "사용 여부")
    private boolean active = true;

    @Schema(description = "공급처 id")
    private long supplierId;

    @Schema(description = "자재 분류 id")
    private long productGroupId;

    @Schema(description = "자재 구분 id")
    private long productTypeId;

    @Schema(description = "자재 속성 id")
    private long productAttributeId;

}