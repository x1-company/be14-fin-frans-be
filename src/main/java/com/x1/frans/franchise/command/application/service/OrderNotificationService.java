package com.x1.frans.franchise.command.application.service;

import com.x1.frans.notification.command.application.service.OrderStatusNotificationSender;
import com.x1.frans.notification.command.application.service.NotificationService;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationService implements OrderStatusNotificationSender {

    private final NotificationService notificationService;

    @Autowired
    public OrderNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void notifyOrderStatusChanged(Long orderId, OrderStatus status, Long receiverId) {
        notificationService.createOrderStatusNotification(orderId, status, receiverId);
    }

}
