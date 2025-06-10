package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dao.OrderQueryMapper;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderQueryMapper orderQueryMapper;

    @Override
    public OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition) {

        // 페이지 오프셋 계산
        int offset = (condition.getPage() - 1) * condition.getSize();
        condition.setOffset(offset);

        // 주문 목록 및 총 개수 조회
        List<OrderSummaryResponseDto> content = orderQueryMapper.searchOrders(condition);
        int totalCount = orderQueryMapper.countOrders(condition);
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
