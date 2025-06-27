package com.x1.frans.statistics.query.view.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.statistics.query.view.dto.FranchiseOrderAmountQueryDTO;
import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;
import com.x1.frans.statistics.query.view.dto.FranchiseReturnProductQueryDTO;
import com.x1.frans.statistics.query.view.service.FranchiseOrderAmountQueryService;
import com.x1.frans.statistics.query.view.service.FranchiseProductOrderQueryService;
import com.x1.frans.statistics.query.view.service.FranchiseReturnProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "📊 월별 가맹점 통계", description = "가맹점주가 확인하는 통계 API")
@RestController
@RequestMapping("/api/franchise/stats")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseOrderAmountQueryService franchiseOrderAmountQueryService;
    private final FranchiseProductOrderQueryService franchiseProductOrderQueryService;
    private final FranchiseReturnProductQueryService franchiseReturnProductQueryService;

    @GetMapping("/order-amounts")
    @Operation(
            summary = "가맹점의 월별 주문 금액 통계 조회",
            description = "가맹점주가 소유한 가맹점의 월별 주문 금액 통계를 조회한다.")
    public ResponseEntity<List<FranchiseOrderAmountQueryDTO>> getOrderAmountStatsByOwnerId (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseOrderAmountQueryDTO> stats
                = franchiseOrderAmountQueryService.getStatsByOwnerId(userId, year, month);

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/order-products")
    @Operation(
            summary = "가맹점의 월별 주문 자재 통계 조회",
            description = "가맹점주가 소유한 가맹점의 월별 자재 주문량 통계를 조회한다."
    )
    public ResponseEntity<List<FranchiseProductOrderQueryDTO>> getProductOrderStatsByManagerId (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseProductOrderQueryDTO> stats
                = franchiseProductOrderQueryService.getStatsByOwnerId(userId, year, month);

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/return-products")
    @Operation(
            summary = "가맹점의 월별 자재 반품 통계 조회",
            description = "가맹점주가 소유한 가맹점의 월별 자재 반품량 통계를 조회한다."
    )
    public ResponseEntity<List<FranchiseReturnProductQueryDTO>> getReturnProductStatsByOwnerId (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseReturnProductQueryDTO> stats
                = franchiseReturnProductQueryService.getStatsByOwnerId(userId, year, month);

        return ResponseEntity.ok(stats);
    }

}
