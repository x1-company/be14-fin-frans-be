package com.x1.frans.returns.command.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "반품 수거일 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnDeliveredAtVO {

    @Schema(name = "수거 완료일")
    private LocalDate deliveredAt;

}
