package com.x1.frans.order.command.application.service;

import com.x1.frans.exception.InvalidRejectConditionException;
import com.x1.frans.exception.OrderDeadlineTimeNotFoundException;
import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.aggregate.StoreOrderDeadline;
import com.x1.frans.order.command.domain.repository.OrderCommandRepository;
import com.x1.frans.order.command.domain.repository.StoreOrderDeadlineRepository;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import com.x1.frans.order.common.OrderAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class FranchiseOrderCommandServiceImpl implements FranchiseOrderCommandService {

    private final OrderAuthorizationService orderAuthorizationService;
    private final OrderCommandRepository orderCommandRepository;
    private final StoreOrderDeadlineRepository deadlineRepository;

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);

        if (order.getStatus() != OrderStatus.WAITING_FOR_RECEIPT) {
            throw new InvalidRejectConditionException("접수 대기 상태에서만 취소할 수 있습니다.");
        }

        StoreOrderDeadline deadline = deadlineRepository.findById(1L)
                .orElseThrow(() -> new OrderDeadlineTimeNotFoundException("주문 마감 시간이 설정되어 있지 않습니다."));

        order.cancelByFranchise(LocalTime.now(), deadline.getOrderDeadlineAt());
        
        orderCommandRepository.save(order);
    }
}
