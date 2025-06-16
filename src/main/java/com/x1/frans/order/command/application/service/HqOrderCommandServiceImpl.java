package com.x1.frans.order.command.application.service;

import com.x1.frans.exception.DuplicateDeadlineTimeException;
import com.x1.frans.exception.InvalidRejectConditionException;
import com.x1.frans.order.command.application.dto.DeliveryInfoRequestDto;
import com.x1.frans.order.command.application.dto.OrderStatusUpdateRequestDto;
import com.x1.frans.order.command.domain.aggregate.Delivery;
import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.aggregate.StoreOrderDeadline;
import com.x1.frans.order.command.domain.repository.DeliveryRepository;
import com.x1.frans.order.command.domain.repository.OrderCommandRepository;
import com.x1.frans.order.command.domain.repository.StoreOrderDeadlineRepository;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import com.x1.frans.order.common.OrderAuthorizationService;
import com.x1.frans.user.enums.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HqOrderCommandServiceImpl implements HqOrderCommandService {

    private final StoreOrderDeadlineRepository storeOrderDeadlineRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderAuthorizationService orderAuthorizationService;
    private final OrderCommandRepository orderCommandRepository;

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

    @Override
    @Transactional
    public void registerOrUpdateDelivery(Long orderId, Long userId, DeliveryInfoRequestDto dto) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);

        OrderStatus currentStatus = order.getStatus();
        if (!(currentStatus.equals(OrderStatus.APPROVED) || currentStatus.equals(OrderStatus.DELIVERING))) {
            throw new InvalidRejectConditionException("배송 정보는 '결재 완료' 또는 '배송 중' 상태에서만 등록/수정할 수 있습니다.");
        }

        Delivery delivery = order.getDelivery();
        boolean isNew = false;

        if (delivery == null) {
            // 최초 등록
            if (dto.getDeliveredAt() != null) {
                throw new InvalidRejectConditionException("배송 정보 등록 시점에는 배송 완료일을 입력할 수 없습니다.");
            }

            delivery = Delivery.builder()
                    .code("DLV-" + System.currentTimeMillis())
                    .status(DeliveryStatus.DELIVERING)
                    .deliveryCompany(dto.getDeliveryCompany())
                    .trackingNumber(dto.getTrackingNumber())
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .build();
            isNew = true;
        } else {
            // 기존 배송 정보 수정
            delivery.updateDeliveryInfo(dto.getDeliveryCompany(), dto.getTrackingNumber(), dto.getName(), dto.getPhone());

            // 배송 완료일이 포함된 경우 → 완료 처리
            if (dto.getDeliveredAt() != null) {
                delivery.completeDelivery(dto.getDeliveredAt());
                order.updateStatus(OrderStatus.DELIVERED);
            }
        }

        deliveryRepository.save(delivery);

        if (isNew) {
            order.assignDelivery(delivery);
            order.updateStatus(OrderStatus.DELIVERING); // 최초 등록 시에만 배송 중으로 전환
        }
    }


    @Override
    public void setOrderStatusToDelivering(List<Long> orderIds) {
        orderCommandRepository.updateOrderStatusToDelivering(orderIds);
    }
}
