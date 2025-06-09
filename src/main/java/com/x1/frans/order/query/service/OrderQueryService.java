package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;

public interface OrderQueryService {
    OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition);
}
