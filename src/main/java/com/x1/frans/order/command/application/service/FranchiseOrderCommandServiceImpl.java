package com.x1.frans.order.command.application.service;

import com.x1.frans.exception.*;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.franchise.command.domain.repository.FranchiseCommandRepository;
import com.x1.frans.order.command.application.dto.FranchiseOrderCreateRequestDto;
import com.x1.frans.order.command.application.dto.OrderMaterialRequestDto;
import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.aggregate.ProductOrder;
import com.x1.frans.order.command.domain.aggregate.StoreOrderDeadline;
import com.x1.frans.order.command.domain.repository.OrderCommandRepository;
import com.x1.frans.order.command.domain.repository.ProductOrderRepository;
import com.x1.frans.order.command.domain.repository.StoreOrderDeadlineRepository;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import com.x1.frans.order.common.OrderAuthorizationService;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseOrderCommandServiceImpl implements FranchiseOrderCommandService {

    private final OrderAuthorizationService orderAuthorizationService;
    private final OrderCommandRepository orderCommandRepository;
    private final StoreOrderDeadlineRepository deadlineRepository;
    private final FranchiseCommandRepository franchiseCommandRepository;
    private final UserCommandRepository userCommandRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(FranchiseOrderCreateRequestDto requestDto, Long userId) {
        FranchiseEntity franchise = franchiseCommandRepository.findById(requestDto.getFranchiseId())
                .orElseThrow(() -> new FranchiseNotFoundException("가맹점을 찾을 수 없습니다."));

        if (!franchise.getOwner().getId().equals(userId)) {
            throw new UnauthorizedAccessException("해당 가맹점에 주문을 등록할 권한이 없습니다.");
        }

        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 주문 생성 (주문 상태 : WAITING_FOR_RECEIPT)
        Order order = Order.builder()
                .code("ORD-" + System.currentTimeMillis())
                .status(OrderStatus.WAITING_FOR_RECEIPT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .totalAmount(BigDecimal.valueOf(0))
                .totalQuantity(0)
                .franchise(franchise)
                .user(user)
                .build();

        orderCommandRepository.save(order);

        // 자재 처리
        List<ProductOrder> productOrders = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (OrderMaterialRequestDto dto : requestDto.getMaterials()) {
            if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
                throw new InvalidRequestException("각 자재의 수량은 필수이며 하나 이상이어야 합니다.");
            }

            ProductEntity product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("자재를 찾을 수 없습니다."));

            int quantity = dto.getQuantity();
            BigDecimal unitPrice = product.getSalePrice();
            BigDecimal itemAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));

            totalQuantity += quantity;
            totalAmount = totalAmount.add(itemAmount);

            ProductOrder productOrder = ProductOrder.builder()
                    .order(order)
                    .product(product)
                    .quantity(quantity)
                    .build();

            productOrders.add(productOrder);
        }


        productOrderRepository.saveAll(productOrders);

        // 총 수량, 총 금액 등록
        order.updateTotal(totalQuantity, totalAmount);

    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderAuthorizationService.getAuthorizedOrder(orderId, userId);

        if (order.getStatus() != OrderStatus.WAITING_FOR_RECEIPT) {
            throw new InvalidRejectConditionException("접수 대기 상태에서만 취소할 수 있습니다.");
        }

        StoreOrderDeadline deadline = deadlineRepository.findById(1L)
                .orElseThrow(() -> new OrderDeadlineTimeNotFoundException("주문 마감 시간이 설정되어 있지 않습니다."));

        order.cancelByFranchise(deadline.getOrderDeadlineAt());

        orderCommandRepository.save(order);
    }


}
