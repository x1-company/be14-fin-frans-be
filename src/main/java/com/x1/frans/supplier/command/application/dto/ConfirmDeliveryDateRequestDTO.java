package com.x1.frans.supplier.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "본사에서 납품일을 확정짓는 DTO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConfirmDeliveryDateRequestDTO {

    @Schema(description = "납품 연도")
    @NotNull
    @Min(2000)
    @Max(2100)
    private Integer year;

    @Schema(description = "납품 월")
    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;

    @Schema(description = "납품 일")
    @NotNull
    @Min(1)
    @Max(31)
    private Integer day;

}
