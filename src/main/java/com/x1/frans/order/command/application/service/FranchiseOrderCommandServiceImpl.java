package com.x1.frans.order.command.application.service;

import com.x1.frans.exception.*;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.franchise.command.domain.repository.FranchiseCommandRepository;
import com.x1.frans.order.command.application.dto.FranchiseOrderCreateRequestDto;
import com.x1.frans.order.command.application.dto.OrderMaterialRequestDto;
import com.x1.frans.order.command.application.dto.OrderTemplateDetailDTO;
import com.x1.frans.order.command.application.dto.OrderTemplateRequestDTO;
import com.x1.frans.order.command.domain.aggregate.*;
import com.x1.frans.order.command.domain.repository.*;
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
import java.util.Optional;

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
    private final OrderTemplateRepository orderTemplateRepository;
    private final OrderTemplateDetailRepository orderTemplateDetailRepository;

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

    @Transactional
    @Override
    public void createTemplate(Long userId, OrderTemplateRequestDTO dto) {
        // 1. 템플릿 생성 (초기 상태)
        OrderTemplate template = OrderTemplate.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .seq(dto.getSeq())
                .userId(userId)
                .build();

        // 2. 상세 항목 반복 추가
        for (OrderTemplateDetailDTO detailDTO : dto.getDetails()) {
            OrderTemplateDetail detail = OrderTemplateDetail.builder()
                    .productId(detailDTO.getProductId())
                    .quantity(detailDTO.getQuantity())
                    .seq(detailDTO.getSeq())
                    .build();

            // 연관관계 세팅
            template.addDetail(detail); // detail.setOrderTemplate(template) 포함됨
        }

        // 3. 저장 (cascade = ALL)
        orderTemplateRepository.save(template);
    }

    @Transactional
    @Override
    public void deleteTemplate(Long userId, String templateId) {
        long id;
        try {
            id = Long.parseLong(templateId);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("유효한 템플릿 id가 아닙니다.");
        }

        // 1. 템플릿 조회 (id 와 userId 모두 조건)
        Optional<OrderTemplate> optTemplate = orderTemplateRepository.findById(id);

        if (optTemplate.isEmpty()) {
            throw new InvalidRequestException("존재하지 않는 템플릿입니다.");
        }

        OrderTemplate template = optTemplate.get();

        // 2. userId 체크
        if (!template.getUserId().equals(userId)) {
            throw new InvalidRequestException("해당 템플릿을 삭제할 권한이 없습니다.");
        }

        // 3. 삭제
        orderTemplateRepository.delete(template);
    }

    @Transactional
    @Override
    public void updateTemplate(Long userId, String templateId, OrderTemplateRequestDTO dto) {
        long id;
        try {
            id = Long.parseLong(templateId);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("유효한 템플릿 id가 아닙니다.");
        }

        // 1. 기존 템플릿 조회 + userId 확인
        OrderTemplate template = orderTemplateRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestException("존재하지 않는 템플릿입니다."));

        if (!template.getUserId().equals(userId)) {
            throw new InvalidRequestException("해당 템플릿을 수정할 권한이 없습니다.");
        }

        // 2. 템플릿 정보 수정
        template = OrderTemplate.builder()
                .id(template.getId()) // 기존 ID 유지
                .name(dto.getName())
                .description(dto.getDescription())
                .seq(dto.getSeq())
                .userId(userId)
                .details(new ArrayList<>()) // 새로 채울 details
                .build();

        // 3. 상세 항목 재등록
        for (OrderTemplateDetailDTO detailDTO : dto.getDetails()) {
            OrderTemplateDetail detail = OrderTemplateDetail.builder()
                    .productId(detailDTO.getProductId())
                    .quantity(detailDTO.getQuantity())
                    .seq(detailDTO.getSeq())
                    .build();

            template.addDetail(detail); // 연관관계 세팅
        }

        // 4. 저장 (details는 orphanRemoval + cascade 덕에 자동 정리됨)
        orderTemplateRepository.save(template);
    }
}
