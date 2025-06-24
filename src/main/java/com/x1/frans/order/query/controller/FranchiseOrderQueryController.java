package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dto.*;
import com.x1.frans.order.query.service.FranchiseOrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/franchise/orders")
@RequiredArgsConstructor
@Tag(name = "📝 가맹점 주문 조회", description = "주문 조회 관련 API")
public class FranchiseOrderQueryController {

    private final FranchiseOrderQueryService franchiseOrderQueryService;

    @GetMapping
    @Operation(
            summary = "주문 목록 조회",
            description = "가맹점 유저가 자신의 주문 목록을 조회합니다."
    )
    public OrderSearchPageResponseDto searchOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute OrderSearchConditionDto condition) {

        Long userId = userDetails.getUserId();
        return franchiseOrderQueryService.searchOrders(condition, userId);
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "주문 상세 조회",
            description = "가맹점 유저가 자신의 주문 상세 정보를 조회합니다."
    )
    public ResponseEntity<FranchiseOrderDetailDto> getOrderDetail(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();

        FranchiseOrderDetailDto dto = franchiseOrderQueryService.getFranchiseOrderDetail(orderId, userId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping({"/templates"})
    public List<OrderTemplateListResponseDto> getTemplates(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return franchiseOrderQueryService.getTemplatesByUser(user.getUserId());
    }

    @GetMapping("/templates/{templateId}")
    public OrderTemplateDetailResponseDto getTemplateDetail(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long templateId) {
        return franchiseOrderQueryService.getTemplateDetail(user.getUserId(), templateId);
    }
}
