package com.x1.frans.order.query.service;

import com.x1.frans.order.query.dto.*;

import java.util.List;

public interface FranchiseOrderQueryService {
    OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId);

    FranchiseOrderDetailDto getFranchiseOrderDetail(Long orderId, Long userId);

    List<OrderTemplateListResponseDto> getTemplatesByUser(Long userId);

    OrderTemplateDetailResponseDto getTemplateDetail(Long userId, Long templateId);
}
