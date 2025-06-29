package com.x1.frans.statistics.query.view.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.statistics.query.view.dto.FranchiseOrderAmountQueryDTO;
import com.x1.frans.statistics.query.view.service.FranchiseOrderAmountQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "📊 통계", description = "통계 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/hq/statistics/franchise/order-amount")
public class FranchiseOrderAmountQueryController {

    private final FranchiseOrderAmountQueryService franchiseOrderAmountQueryService;

    @Autowired
    public FranchiseOrderAmountQueryController (FranchiseOrderAmountQueryService franchiseOrderAmountQueryService) {
        this.franchiseOrderAmountQueryService = franchiseOrderAmountQueryService;
    }

    @Operation(
            summary = "담당 가맹점 월 주문 금액 통계 조회",
            description = "자신이 담당하는 가맹점들의 월 주문 금액 통계를 조회한다.")
    @GetMapping("/manager")
    public ResponseEntity<List<FranchiseOrderAmountQueryDTO>> getManagerStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseOrderAmountQueryDTO> stats
                = franchiseOrderAmountQueryService.getMonthlyStatsByManager(userId, year, month);

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "담당 가맹점의 월별 주문 금액 통계 조회",
            description = "자신이 담당하는 가맹점의 월별 주문 금액 통계를 조회한다.")
    @GetMapping("/manager/{franchiseId}")
    public ResponseEntity<List<FranchiseOrderAmountQueryDTO>> getManagerStatsByFranchiseId (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long franchiseId
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseOrderAmountQueryDTO> stats
                = franchiseOrderAmountQueryService.getMonthlyStatsByManagerByFranchiseId(userId, franchiseId);
        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "부서별 가맹점 월 주문 금액 통계 조회",
            description = "자신이 속한 부서가 담당하는 가맹점들의 월 주문 금액 통계를 조회한다.")
    @GetMapping("/department")
    public ResponseEntity<List<FranchiseOrderAmountQueryDTO>> getDepartmentStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        Long deptId = customUserDetails.getDepartmentId();
        List<FranchiseOrderAmountQueryDTO> stats
                = franchiseOrderAmountQueryService.getMonthlyStatsByDepartment(deptId, year, month);

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "부서별 가맹점의 월별 주문 금액 통계 조회",
            description = "자신이 속한 부서가 담당하는 특정 가맹점의 월별 주문 금액 통계를 조회한다.")
    @GetMapping("/department/{franchiseId}")
    public ResponseEntity<List<FranchiseOrderAmountQueryDTO>> getDepartmentStatsByFranchiseId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long franchiseId
    ) {
        Long deptId = customUserDetails.getDepartmentId();
        List<FranchiseOrderAmountQueryDTO> stats =
                franchiseOrderAmountQueryService.getMonthlyStatsByDepartmentByFranchiseId(deptId, franchiseId);
        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "전체 가맹점 월 주문 금액 통계 조회",
            description = "부서와 직책에 따라 전체 가맹점 통계를 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<List<FranchiseOrderAmountQueryDTO>> getAllStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        Long departmentId = customUserDetails.getDepartmentId();
        Long dutyId = customUserDetails.getDutyId();

        List<FranchiseOrderAmountQueryDTO> stats
                = franchiseOrderAmountQueryService.getMonthlyStatsForAllByDuty(userId, departmentId, dutyId);

        return ResponseEntity.ok(stats);
    }


}
