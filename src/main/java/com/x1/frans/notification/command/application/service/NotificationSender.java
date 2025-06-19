package com.x1.frans.notification.command.application.service;

import com.x1.frans.order.command.domain.vo.OrderStatus;

public interface NotificationSender {

    void notifyOrderStatusChanged(Long orderId, OrderStatus status, Long receiverId);

}
