package com.x1.frans.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OrderTemplateListResponseDto {

    @Schema(description = "주문 템플릿 ID")
    private Long id;

    @Schema(description = "주문 템플릿 이름")
    private String name;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "순서")
    private Integer seq;

}

