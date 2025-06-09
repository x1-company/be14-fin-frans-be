package com.x1.frans.order.query.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderSearchPageResponseDto {
    private List<OrderSummaryResponseDto> content;
    private int totalCount;
    private int page;
    private int size;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
