package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dao.FranchiseOrderQueryMapper;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.service.OrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
            @ModelAttribute OrderSearchConditionDto condition
    ) {
        Long userId = Long.valueOf(userDetails.getUserId());
        Long franchiseId = franchiseOrderQueryMapper.findFranchiseIdByUserId(userId);

        int offset = condition.getSize() * (condition.getPage() - 1);
        condition.setFranchiseId(franchiseId);
        condition.setOffset(offset);

        return orderQueryService.searchOrders(condition);
    }
}
