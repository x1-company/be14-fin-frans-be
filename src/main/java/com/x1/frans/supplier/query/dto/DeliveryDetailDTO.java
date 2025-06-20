package com.x1.frans.supplier.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "납품 상세 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryDetailDTO {

    @Schema(description = "납품 상세 id")
    private Long id;

    @Schema(description = "총 수량")
    private Integer quantity;

    @Schema(description = "총 금액")
    private BigDecimal totalAmount;

    @Schema(description = "자재 id")
    private Long productId;

    @Schema(description = "납품서 id")
    private Long deliveryInfoId;

}
