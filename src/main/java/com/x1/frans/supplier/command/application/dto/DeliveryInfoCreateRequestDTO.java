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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryInfoCreateRequestDTO {

    @Schema(description = "발주 id")
    @NotNull
    private Long purchaseOrderId;

    @Schema(description = "납품 예정일")
    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expectedDate;

    @Schema(description = "배송 업체명")
    @NotNull
    private String deliveryCompanyName;

    @Schema(description = "배송 차량 번호")
    @NotNull
    private String vehicleNumber;

    @Schema(description = "운송장 번호")
    @NotNull
    private String trackingNumber;

    @Schema(description = "자재 리스트")
    @NotNull
    @Valid
    private List<DeliveryItemDTO> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DeliveryItemDTO {

        @Schema(description = "자재 id")
        @NotNull
        private Long productId;

        @Schema(description = "수량")
        @Min(1)
        private Integer quantity;

    }

}
