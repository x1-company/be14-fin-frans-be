package com.x1.frans.order.command.application.service;

import java.time.LocalTime;

public interface StoreOrderDeadlineService {
    boolean createOrUpdateDeadline(LocalTime deadlineTime);
}
