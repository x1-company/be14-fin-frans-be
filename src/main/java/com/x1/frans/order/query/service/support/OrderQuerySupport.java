package com.x1.frans.order.query.service.support;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderQuerySupport {

    public void applyPagination(OrderSearchConditionDto condition) {
        int offset = (condition.getPage() - 1) * condition.getSize();
        condition.setOffset(offset);
    }

    public OrderSearchPageResponseDto buildPagedResponse(
            OrderSearchConditionDto condition,
            List<OrderSummaryResponseDto> content,
            int totalCount
    ) {
        int totalPages = (int) Math.ceil((double) totalCount / condition.getSize());

        return OrderSearchPageResponseDto.builder()
                .content(content)
                .totalCount(totalCount)
                .page(condition.getPage())
                .size(condition.getSize())
                .totalPages(totalPages)
                .hasNext(condition.getPage() < totalPages)
                .hasPrevious(condition.getPage() > 1)
                .build();
    }

}
