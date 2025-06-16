package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dao.FranchiseOrderQueryMapper;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseOrderQueryServiceImpl implements FranchiseOrderQueryService {

    private final FranchiseOrderQueryMapper franchiseOrderQueryMapper;

    @Override
    @Transactional
    public OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId) {
        condition.setOwnerId(userId);

        int offset = (condition.getPage() - 1) * condition.getSize();
        condition.setOffset(offset);

        List<OrderSummaryResponseDto> content = franchiseOrderQueryMapper.searchOrders(condition);
        int totalCount = franchiseOrderQueryMapper.countOrders(condition);
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
