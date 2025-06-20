package com.x1.frans.supplier.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "본사 직원과 공급처 직원이 납품서를 수정하기 위한 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryInfoModifyDTO {

    @Schema(description = "납품서 id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    @Schema(description = "납품 예정일")
    private LocalDate expectedDate;

    @Schema(description = "배송 업체명")
    private String deliveryCompanyName;

    @Schema(description = "배송 차량 번호")
    private String vehicleNumber;

    @Schema(description = "운송장 번호")
    private String trackingNumber;

    @Schema(description = "자재 리스트")
    @Valid
    private List<DeliveryItemModifyDTO> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DeliveryItemModifyDTO {

        @Schema(description = "자재 id")
        @NotNull
        private Long productId;

        @Schema(description = "수량")
        @Min(1)
        private Integer quantity;

    }
}
