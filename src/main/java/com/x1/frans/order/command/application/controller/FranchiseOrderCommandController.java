package com.x1.frans.order.command.application.controller;

import com.x1.frans.order.command.application.service.FranchiseOrderCommandService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/franchise/orders")
@Tag(name = "📝 가맹점 주문 처리", description = "주문 상태 변경, 처리 관련 API")
public class FranchiseOrderCommandController {

    private final FranchiseOrderCommandService franchiseOrderCommandService;

    @PatchMapping("/{orderId}/cancel")
    @Operation(
            summary = "주문 취소 처리",
            description = "가맹점주가 접수 대기 상태인 주문을 취소합니다."
    )
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        franchiseOrderCommandService.cancelOrder(orderId, userDetails.getUserId());
        return ResponseEntity.ok().build();
    }
}
