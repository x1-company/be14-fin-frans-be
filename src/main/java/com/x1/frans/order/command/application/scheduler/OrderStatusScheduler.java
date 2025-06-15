package com.x1.frans.order.command.application.scheduler;

import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.repository.OrderCommandRepository;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import com.x1.frans.order.query.dao.StoreOrderDeadlineQueryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderStatusScheduler {

    private final StoreOrderDeadlineQueryMapper deadlineQueryMapper;
    private final OrderCommandRepository orderCommandRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateOrdersAfterDeadline() {
        LocalTime deadline = deadlineQueryMapper.findLatestDeadline();
        if (deadline == null) return;

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadlineDateTime = LocalDateTime.of(today, deadline);

        // 마감 시간 전이면 스킵
        if (now.isBefore(deadlineDateTime)) return;

        // Redis에 이미 처리했는지 확인
        String redisKey = "deadline:processed:" + today;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) return;

        // 주문 상태 일괄 변경
        List<Order> waitingOrders = orderCommandRepository.findByStatusAndCreatedAtBefore(
                OrderStatus.WAITING_FOR_RECEIPT,
                deadlineDateTime
        );

        waitingOrders.forEach(order -> order.updateOrderStatus(OrderStatus.REVIEWING));

        // Redis에 처리 완료 키 저장 (TTL 24시간)
        redisTemplate.opsForValue().set(redisKey, "true", Duration.ofHours(24));

    }
}
