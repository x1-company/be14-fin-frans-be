package com.x1.frans.order.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderSearchConditionDto {
    private Long franchiseId;
    private String filterType;
    private String keyword;
    private int page;
    private int size;           // 한 페이지당 목록 개수
    private int offset;         // size * (page -1)
}
