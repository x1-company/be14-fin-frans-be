package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dao.FranchiseOrderQueryMapper;
import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.service.HqOrderQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/franchise/orders")
@RequiredArgsConstructor
@Tag(name = "📝 가맹점 주문 조회", description = "주문 조회 관련 API")
public class FranchiseOrderQueryController {

    private final HqOrderQueryService orderQueryService;
    private final FranchiseOrderQueryMapper franchiseOrderQueryMapper; // userId → franchiseId 조회용

    @GetMapping
    @Operation(
            summary = "주문 목록 조회",
            description = "본인이 속한 부서가 관리하는 가맹점의 주문 목록을 조회합니다."
    )
    public OrderSearchPageResponseDto searchOrdersForFranchise(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute OrderSearchConditionDto condition
    ) {
        Long userId = Long.valueOf(userDetails.getUserId());
        List<Long> franchiseIds = franchiseOrderQueryMapper.findFranchiseIdsByUserId(userId);

        int offset = condition.getSize() * (condition.getPage() - 1);
        condition.setOffset(offset);

        // ✅ 프랜차이즈 유저는 자신이 소속된 가맹점만 조회하도록 설정
        condition.setDepartmentFranchiseIds(franchiseIds);

        return orderQueryService.searchOrders(condition, userId);
    }
}
