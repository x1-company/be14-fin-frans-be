package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.service.FranchiseOrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/franchise/orders")
@RequiredArgsConstructor
@Tag(name = "📝 가맹점 주문 조회", description = "주문 조회 관련 API")
public class FranchiseOrderQueryController {

    private final FranchiseOrderQueryService franchiseOrderQueryService;

    @GetMapping
    @Operation(summary = "주문 목록 조회", description = "가맹점 유저가 자신의 주문 목록을 조회합니다.")
    public OrderSearchPageResponseDto searchOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute OrderSearchConditionDto condition) {

        Long userId = userDetails.getUserId();
        return franchiseOrderQueryService.searchOrders(condition, userId);
    }
}
