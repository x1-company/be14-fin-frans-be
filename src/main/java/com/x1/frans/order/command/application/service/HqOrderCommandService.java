package com.x1.frans.order.command.application.service;

import java.time.LocalTime;

public interface HqOrderCommandService {
    boolean createOrUpdateDeadline(LocalTime deadlineTime);

    void rejectOrder(Long orderId, String reason, Long userId);

    void markReviewComplete(Long orderId, Long userId);

    void cancelReviewComplete(Long orderId, Long userId);
}
