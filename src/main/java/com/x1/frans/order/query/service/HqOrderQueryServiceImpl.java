package com.x1.frans.order.query.service;

import com.x1.frans.exception.OrderNotFoundException;
import com.x1.frans.order.common.OrderAuthorizationService;
import com.x1.frans.order.query.dao.HqOrderQueryMapper;
import com.x1.frans.order.query.dto.*;
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
    public HqOrderDetailDto getOrderDetail(Long orderId, Long userId) {
        orderAuthorizationService.getAuthorizedOrder(orderId, userId);

        HqOrderDetailDto detail = orderQueryMapper.findOrderDetailById(orderId);
        if (detail == null) {
            throw new OrderNotFoundException("주문 상세 정보를 찾을 수 없습니다.");
        }

        List<ProductDetailDTO> products = orderQueryMapper.findProductsByOrderId(orderId);
        orderDetailCalculator.fillDetails(detail, products);
        return detail;
    }

    @Override
    public List<OrderReviewCompletedListDto> getOrderReviewCompleted(Long userId) {
        return orderQueryMapper.findOrderReviewCompleted(userId);
    }
}

}
