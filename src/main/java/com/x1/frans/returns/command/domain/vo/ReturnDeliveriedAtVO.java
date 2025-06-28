package com.x1.frans.returns.command.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "반품 회수 날짜 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnDeliveriedAtVO {

    @Schema(name = "반품 회수 날짜")
    private LocalDate deliveriedAt;
}
