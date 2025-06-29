package com.x1.frans.statistics.query.view.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;
import com.x1.frans.statistics.query.view.service.FranchiseProductOrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "📊 통계", description = "통계 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/hq/statistics/franchise/product-order")
public class FranchiseProductOrderQueryController {

    private final FranchiseProductOrderQueryService franchiseProductOrderQueryService;

    @Autowired
    public FranchiseProductOrderQueryController(FranchiseProductOrderQueryService franchiseProductOrderQueryService) {
        this.franchiseProductOrderQueryService = franchiseProductOrderQueryService;
    }

    @Operation(
            summary = "가맹점 월별 자재 주문량 통계 조회",
            description = "해당 가맹점의 이전 달 통계를 조회한다.")
    @GetMapping
    public ResponseEntity<List<FranchiseProductOrderQueryDTO>> getFranchiseProductOrderStat(@RequestParam long franchiseId,
                                                                                            @RequestParam int year,
                                                                                            @RequestParam int month) {

        List<FranchiseProductOrderQueryDTO> stats = franchiseProductOrderQueryService.getStats(franchiseId, year, month);

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "담당 가맹점 월 자재별 주문량 통계 조회",
            description = "자신이 담당하는 가맹점들의 월 자재별 주문량 통계를 조회한다.")
    @GetMapping("/manager")
    public ResponseEntity<List<FranchiseProductOrderQueryDTO>> getManagerStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseProductOrderQueryDTO> stats
                = franchiseProductOrderQueryService.getMonthlyStatsByManager(userId, year, month);

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/manager/{franchiseId}")
    public ResponseEntity<List<FranchiseProductOrderQueryDTO>> getManagerStatsByFranchiseId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long franchiseId  // ← year, month 제거
    ) {
        Long userId = customUserDetails.getUserId();
        List<FranchiseProductOrderQueryDTO> stats =
                franchiseProductOrderQueryService.getMonthlyStatsByManagerByFranchiseId(userId, franchiseId);  // ← year, month 제거
        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "부서별 가맹점 월 자재별 주문량 통계 조회",
            description = "자신이 속한 부서가 담당하는 가맹점들의 월 자재별 주문량 통계를 조회한다."
    )
    @GetMapping("/department")
    public ResponseEntity<List<FranchiseProductOrderQueryDTO>> getDepartmentStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        Long deptId = customUserDetails.getDepartmentId();

        List<FranchiseProductOrderQueryDTO> stats = franchiseProductOrderQueryService
                .getMonthlyStatsByDepartment(deptId, year, month);

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "부서별 특정 가맹점의 월별 자재 주문량 통계 조회",
            description = "자신이 속한 부서가 담당하는 특정 가맹점의 월별 자재 주문량 통계를 조회한다."
    )
    @GetMapping("/department/{franchiseId}")
    public ResponseEntity<List<FranchiseProductOrderQueryDTO>> getDepartmentStatsByFranchiseId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long franchiseId
    ) {
        Long deptId = customUserDetails.getDepartmentId();
        List<FranchiseProductOrderQueryDTO> stats =
                franchiseProductOrderQueryService.getMonthlyStatsByDepartmentByFranchiseId(deptId, franchiseId);
        return ResponseEntity.ok(stats);
    }

}
