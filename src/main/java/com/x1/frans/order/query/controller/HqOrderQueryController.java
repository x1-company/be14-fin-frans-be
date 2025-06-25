package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dto.*;
import com.x1.frans.order.query.service.HqOrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hq/orders")
@RequiredArgsConstructor
@Tag(name = "📝 주문 조회", description = "주문 조회 관련 API")
public class HqOrderQueryController {

    private final HqOrderQueryService orderQueryService;

    @GetMapping
    @Operation(
            summary = "주문 목록 조회",
            description = "본인이 속한 부서가 관리하는 가맹점의 주문 목록을 조회합니다."
    )
    public OrderSearchPageResponseDto searchOrdersPaged(
            @ModelAttribute OrderSearchConditionDto condition,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        return orderQueryService.searchOrders(condition, userId);
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "주문 상세 조회",
            description = "주문 ID로 주문 상세 정보를 조회합니다."
    )
    public ResponseEntity<HqOrderDetailDto> getOrderDetail(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();

        HqOrderDetailDto dto = orderQueryService.getOrderDetail(orderId, userId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reviewCompleted")
    @Operation(
            summary = "(검토 완료 상태인) 주문 목록 조회 ",
            description = "주문 ID로 주문 상세 정보를 조회합니다."
    )
    public ResponseEntity<List<OrderReviewCompletedListDto>> getOrderReviewCompleted(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();

        List<OrderReviewCompletedListDto> dto = orderQueryService.getOrderReviewCompleted(userId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/deadline")
    public ResponseEntity<OrderDeadlineResponseDto> getOrderDeadline(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();  // 예: 추후 HQ 소속 체크 등에 사용할 수 있음

        OrderDeadlineResponseDto dto = orderQueryService.getOrderDeadline(userId);

        return ResponseEntity.ok(dto);
    }

}
