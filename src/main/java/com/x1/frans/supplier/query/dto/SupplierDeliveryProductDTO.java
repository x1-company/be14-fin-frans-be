package com.x1.frans.supplier.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "공급처 납품 정보 등록에서 자재 목록 조회 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierDeliveryProductDTO {

    @Schema(description = "자재 id")
    private Long id;

    @Schema(description = "자재명")
    private String name;

    @Schema(description = "자재 코드")
    private String code;

    @Schema(description = "판매 단가")
    private BigDecimal salePrice;

    @Schema(description = "판매 단위")
    private String saleUnit;

    @Schema(description = "단위")
    private String unit;

    @Schema(description = "규격")
    private String spec;

}
