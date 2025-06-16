package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;

public interface FranchiseOrderQueryService {
    OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId);
}
