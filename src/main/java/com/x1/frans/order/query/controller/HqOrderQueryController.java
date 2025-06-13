package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dto.OrderDetailDto;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.service.HqOrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hq/orders")
@RequiredArgsConstructor
public class HqOrderQueryController {
    private final HqOrderQueryService orderQueryService;

    @GetMapping
    public OrderSearchPageResponseDto searchOrdersPaged(
            @ModelAttribute OrderSearchConditionDto condition
    ) {
        return orderQueryService.searchOrders(condition);
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "주문 상세 조회",
            description = "주문 ID로 주문 상세 정보를 조회합니다."
    )
    public ResponseEntity<OrderDetailDto> getOrderDetail(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();

        OrderDetailDto dto = orderQueryService.getOrderDetail(orderId, userId);

        return ResponseEntity.ok(dto);
    }
}
