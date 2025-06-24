package com.x1.frans.order.query.service;

import com.x1.frans.exception.OrderNotFoundException;
import com.x1.frans.exception.OrderTemplateNotFoundException;
import com.x1.frans.order.query.dao.FranchiseOrderQueryMapper;
import com.x1.frans.order.query.dto.*;
import com.x1.frans.order.query.service.support.OrderDetailCalculator;
import com.x1.frans.product.query.dto.ProductDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseOrderQueryServiceImpl implements FranchiseOrderQueryService {

    private final FranchiseOrderQueryMapper franchiseOrderQueryMapper;
    private final OrderDetailCalculator orderDetailCalculator;

    @Override
    @Transactional
    public OrderSearchPageResponseDto searchOrders(OrderSearchConditionDto condition, Long userId) {
        condition.setOwnerId(userId);

        int offset = (condition.getPage() - 1) * condition.getSize();
        condition.setOffset(offset);

        List<OrderSummaryResponseDto> content = franchiseOrderQueryMapper.searchOrders(condition);
        int totalCount = franchiseOrderQueryMapper.countOrders(condition);
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
    @Transactional(readOnly = true)
    public FranchiseOrderDetailDto getFranchiseOrderDetail(Long orderId, Long userId) {
        FranchiseOrderDetailDto detail = franchiseOrderQueryMapper.findFranchiseOrderDetailById(orderId, userId);
        if (detail == null) {
            throw new OrderNotFoundException("해당 주문을 찾을 수 없거나 접근 권한이 없습니다.");
        }

        List<ProductDetailDTO> products = franchiseOrderQueryMapper.findProductsByOrderId(orderId);
        orderDetailCalculator.frfillDetails(detail, products);
        return detail;
    }

    @Override
    public List<OrderTemplateListResponseDto> getTemplatesByUser(Long userId) {
        return franchiseOrderQueryMapper.findTemplatesByUserId(userId);
    }

    @Override
    public OrderTemplateDetailResponseDto getTemplateDetail(Long userId, Long templateId) {
        OrderTemplateDetailResponseDto detail = franchiseOrderQueryMapper.findTemplateDetail(templateId, userId);
        if (detail == null) {
            throw new OrderTemplateNotFoundException("조회 권한이 없거나 존재하지 않는 템플릿입니다.");
        }
        List<ProductInTemplateDto> products = franchiseOrderQueryMapper.findProductsByTemplateId(templateId);
        detail.getProducts().addAll(products);
        return detail;
    }
}
