package com.x1.frans.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderTemplateDetailResponseDto {

    @Schema(description = "순서")
    private Integer seq;

    @Schema(description = "자재")
    private List<ProductInTemplateDto> products = new ArrayList<>();

    public List<ProductInTemplateDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInTemplateDto> products) {
        this.products = products;
    }
}
