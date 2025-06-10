package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dao.FranchiseOrderQueryMapper;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.service.OrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/franchise/orders")
@RequiredArgsConstructor
public class FranchiseOrderQueryController {

    private final OrderQueryService orderQueryService;
    private final FranchiseOrderQueryMapper franchiseOrderQueryMapper; // userId → franchiseId 조회용

    @GetMapping
    public OrderSearchPageResponseDto searchOrdersForFranchise(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String product,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = Long.valueOf(userDetails.getUserId());
        Long franchiseId = franchiseOrderQueryMapper.findFranchiseIdByUserId(userId);

        int offset = size * (page - 1);

        OrderSearchConditionDto condition = OrderSearchConditionDto.builder()
                .franchiseId(franchiseId)
                .filterType(filterType)
                .keyword(keyword)
                .code(code)
                .product(product)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .page(page)
                .size(size)
                .offset(offset)
                .build();

        return orderQueryService.searchOrders(condition);
    }
}
