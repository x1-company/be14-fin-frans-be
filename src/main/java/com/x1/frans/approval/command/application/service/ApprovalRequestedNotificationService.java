package com.x1.frans.approval.command.application.service;

import com.x1.frans.notification.command.application.service.ApprovalRequestNotificationSender;
import com.x1.frans.notification.command.application.service.NotificationService;
import com.x1.frans.notification.command.domain.model.NotificationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalRequestedNotificationService implements ApprovalRequestNotificationSender {

    private final NotificationService notificationService;

    @Autowired
    public ApprovalRequestedNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void notifyApprovalRequested(Long approvalId, Long receiverId, NotificationTarget target) {
        notificationService.createApprovalLineNotification(approvalId, receiverId, target);
    }

    @Override
    public void notifyApprovalRejected(Long approvalId, Long receiverId, NotificationTarget target) {
        notificationService.createApprovalRejectedNotification(approvalId, receiverId, target);
    }
}
