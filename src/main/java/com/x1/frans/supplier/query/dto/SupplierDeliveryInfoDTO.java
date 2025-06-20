package com.x1.frans.supplier.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "납품서 조회 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierDeliveryInfoDTO {

    @Schema(description = "납품 id")
    private Long id;

    @Schema(description = "납품 코드")
    private String code;

    @Schema(description = "납품 연도")
    private Integer year;

    @Schema(description = "납품 월")
    private Integer month;

    @Schema(description = "납품 일")
    private Integer day;

    @Schema(description = "총 금액")
    private BigDecimal totalAmount;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "수정일")
    private LocalDateTime updatedAt;

    @Schema(description = "배송 업체")
    private String deliveryCompanyName;

    @Schema(description = "차량 번호")
    private String vehicleNumber;

    @Schema(description = "운송장 번호")
    private String trackingNumber;

    @Schema(description = "공급처 id")
    private Long supplierId;

    @Schema(description = "발주 id")
    private Long purchaseOrderId;

    @Schema(description = "납품 상세 목록")
    private List<DeliveryDetailDTO> details;

}
