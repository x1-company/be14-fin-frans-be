package com.x1.frans.order.command.application.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderDeadlineUtils {
    public static LocalDate calculateProcessingDate(LocalDateTime orderTime, LocalTime deadlineTime) {
        return orderTime.toLocalTime().isAfter(deadlineTime)
                ? orderTime.toLocalDate().plusDays(1)
                : orderTime.toLocalDate();
    }

    public static boolean canCancelOrder(LocalDateTime orderCreatedAt, LocalTime deadlineTime, LocalDateTime currentTime) {
        LocalDate processingDate = calculateProcessingDate(orderCreatedAt, deadlineTime);
        LocalDateTime processingDeadline = processingDate.atTime(deadlineTime);
        return currentTime.isBefore(processingDeadline);
    }
}
