package com.x1.frans.order.command.application.service;

import com.x1.frans.order.command.application.dto.DeliveryInfoRequestDto;
import com.x1.frans.order.command.application.dto.OrderStatusUpdateRequestDto;

import java.time.LocalTime;
import java.util.List;

public interface HqOrderCommandService {
    boolean createOrUpdateDeadline(LocalTime deadlineTime);

    void rejectOrder(Long orderId, String reason, Long userId);

    void markReviewComplete(Long orderId, Long userId);

    void cancelReviewComplete(Long orderId, Long userId);

    void updateOrderStatusAndDelivery(Long orderId, OrderStatusUpdateRequestDto dto, Long userId);

    void registerOrUpdateDelivery(Long orderId, Long userId, DeliveryInfoRequestDto dto);

    void setOrderStatusToDelivering(List<Long> orderIds);
}
