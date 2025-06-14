package com.x1.frans.order.query.service;

import com.x1.frans.exception.OrderNotFoundException;
import com.x1.frans.order.common.OrderAuthorizationService;
import com.x1.frans.order.query.dao.HqOrderQueryMapper;
import com.x1.frans.order.query.dto.OrderDetailDto;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import com.x1.frans.product.query.dto.ProductDetailDTO;
import com.x1.frans.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HqOrderQueryServiceImpl implements HqOrderQueryService {

    private final HqOrderQueryMapper orderQueryMapper;
    private final OrderAuthorizationService orderAuthorizationService;
    private final UserQueryService userQueryService;

    @Override
    @Transactional
    public OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId) {

        // ✅ franchiseId가 null이면 HQ 유저, null 아니면 프랜차이즈 유저로 간주
        if (condition.getDepartmentFranchiseIds() == null || condition.getDepartmentFranchiseIds().isEmpty()) {
            // 👉 HQ 유저만 접근 가능한 경우
            List<Long> allowedFranchiseIds = userQueryService.getAccessibleFranchiseIdsForUser(userId);
            condition.setDepartmentFranchiseIds(allowedFranchiseIds);
        }

        // 페이지 오프셋 계산
        int offset = (condition.getPage() - 1) * condition.getSize();
        condition.setOffset(offset);

        // 주문 목록 및 총 개수 조회
        List<OrderSummaryResponseDto> content = orderQueryMapper.searchOrders(condition);
        int totalCount = orderQueryMapper.countOrders(condition);
        int totalPages = (int) Math.ceil((double) totalCount / condition.getSize());

        return OrderSearchPageResponseDto.builder()
                .content(content)
                .totalCount(totalCount)
                .page(condition.getPage())
                .size(condition.getSize())
                .totalPages(totalPages)
                .hasNext(condition.getPage() < totalPages)
                .hasPrevious(condition.getPage() > 1)
                .build();
    }


    @Override
    @Transactional
    public OrderDetailDto getOrderDetail(Long orderId, Long userId) {
        // 권한 검증 및 주문 조회
        orderAuthorizationService.getAuthorizedOrder(orderId, userId);

        // 상세 정보 조회
        OrderDetailDto detail = orderQueryMapper.findOrderDetailById(orderId);
        if (detail == null) {
            throw new OrderNotFoundException("주문 상세 정보를 찾을 수 없습니다.");
        }

        // 상품 목록 조회
        List<ProductDetailDTO> products = orderQueryMapper.findProductsByOrderId(orderId);
        detail.setProducts(products);

        // 총 수량 계산
        int totalQty = products.stream()
                .mapToInt(p -> Optional.ofNullable(p.getQuantity()).orElse(0))
                .sum();
        detail.setTotalQuantity(totalQty);

        // 총 금액 계산
        BigDecimal totalAmount = products.stream()
                .map(p -> {
                    BigDecimal price = Optional.ofNullable(p.getSalePrice()).orElse(BigDecimal.ZERO);
                    int qty = Optional.ofNullable(p.getQuantity()).orElse(0);
                    return price.multiply(BigDecimal.valueOf(qty));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        detail.setTotalAmount(totalAmount);

        return detail;
    }

}
