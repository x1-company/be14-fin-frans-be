package com.x1.frans.notification.command.application.service;

import com.x1.frans.notification.command.domain.model.NotificationTarget;

public interface ApprovalRequestNotificationSender {

    void notifyApprovalRequested(Long approvalId, Long receiverId, NotificationTarget target);

    void notifyApprovalRejected(Long approvalId, Long receiverId, NotificationTarget target);

}
