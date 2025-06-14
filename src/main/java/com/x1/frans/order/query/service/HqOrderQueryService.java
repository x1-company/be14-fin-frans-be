package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dto.OrderDetailDto;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;

public interface HqOrderQueryService {
    OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId);

    OrderDetailDto getOrderDetail(Long orderId, Long userId);
}
