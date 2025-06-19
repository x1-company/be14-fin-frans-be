package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dto.HqOrderDetailDto;
import com.x1.frans.order.query.dto.OrderReviewCompletedListDto;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;

import java.util.List;

public interface HqOrderQueryService {
    OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId);

    HqOrderDetailDto getOrderDetail(Long orderId, Long userId);

    List<OrderReviewCompletedListDto> getOrderReviewCompleted(Long userId);
}
