package com.x1.frans.order.query.service;

import com.x1.frans.exception.OrderNotFoundException;
import com.x1.frans.order.common.OrderAuthorizationService;
import com.x1.frans.order.query.dao.HqOrderQueryMapper;
import com.x1.frans.order.query.dto.OrderDetailDto;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import com.x1.frans.order.query.service.support.OrderDetailCalculator;
import com.x1.frans.order.query.service.support.OrderQuerySupport;
import com.x1.frans.product.query.dto.ProductDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HqOrderQueryServiceImpl implements HqOrderQueryService {

    private final HqOrderQueryMapper orderQueryMapper;
    private final OrderAuthorizationService orderAuthorizationService;
    private final OrderQuerySupport orderQuerySupport;
    private final OrderDetailCalculator orderDetailCalculator;

    @Override
    @Transactional
    public OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId) {
        condition.setUserId(userId);

        orderQuerySupport.applyPagination(condition);
        List<OrderSummaryResponseDto> content = orderQueryMapper.searchOrders(condition);
        int totalCount = orderQueryMapper.countOrders(condition);

        return orderQuerySupport.buildPagedResponse(condition, content, totalCount);
    }

    @Override
    @Transactional
    public OrderDetailDto getOrderDetail(Long orderId, Long userId) {
        validateOrderAccess(orderId, userId);
        OrderDetailDto detail = fetchOrderDetail(orderId);
        List<ProductDetailDTO> products = fetchProductDetails(orderId);
        orderDetailCalculator.fillDetails(detail, products);
        return detail;
    }

    private void validateOrderAccess(Long orderId, Long userId) {
        orderAuthorizationService.getAuthorizedOrder(orderId, userId);
    }

    private OrderDetailDto fetchOrderDetail(Long orderId) {
        OrderDetailDto detail = orderQueryMapper.findOrderDetailById(orderId);
        if (detail == null) {
            throw new OrderNotFoundException("주문 상세 정보를 찾을 수 없습니다.");
        }
        return detail;
    }

    private List<ProductDetailDTO> fetchProductDetails(Long orderId) {
        return orderQueryMapper.findProductsByOrderId(orderId);
    }
}
