package com.x1.frans.order.command.application.service;

import com.x1.frans.exception.DuplicateDeadlineTimeException;
import com.x1.frans.order.command.application.dto.OrderStatusUpdateRequestDto;
import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.aggregate.StoreOrderDeadline;
import com.x1.frans.order.command.domain.repository.StoreOrderDeadlineRepository;
import com.x1.frans.order.common.OrderAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class HqOrderCommandServiceImpl implements HqOrderCommandService {

    private final StoreOrderDeadlineRepository storeOrderDeadlineRepository;
    private final OrderAuthorizationService orderAuthorizationService;

    @Override
    @Transactional
    public boolean createOrUpdateDeadline(LocalTime deadlineTime) {
        StoreOrderDeadline deadline = storeOrderDeadlineRepository.findById(1L).orElse(null);

        if (deadline == null) {
            StoreOrderDeadline newDeadline = new StoreOrderDeadline(deadlineTime);
            storeOrderDeadlineRepository.save(newDeadline);
            return true;
        } else {
            if (deadline.getOrderDeadlineAt().equals(deadlineTime)) {
                throw new DuplicateDeadlineTimeException("기존과 동일한 주문 마감 시간입니다.");
            }
            deadline.updateDeadlineTime(deadlineTime);
            return false;
        }
    }

    @Override
    @Transactional
    public void rejectOrder(Long orderId, String reason, Long userId) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);
        order.reject(reason);
    }

    @Override
    @Transactional
    public void markReviewComplete(Long orderId, Long userId) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);
        order.markReviewComplete();
    }

    @Override
    @Transactional
    public void cancelReviewComplete(Long orderId, Long userId) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);
        order.cancelReviewComplete();
    }

    @Override
    @Transactional
    public void updateOrderStatusAndDelivery(Long orderId, OrderStatusUpdateRequestDto dto, Long userId) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);
        order.updateStatus(dto.getStatus());
    }


}
