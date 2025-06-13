package com.x1.frans.statistics.query.view.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.statistics.query.view.dto.FranchiseReturnProductQueryDTO;
import com.x1.frans.statistics.query.view.service.FranchiseReturnProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "📊 통계", description = "통계 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/hq/statistics/franchise/return-product")
public class FranchiseReturnProductQueryController {

    private final FranchiseReturnProductQueryService franchiseReturnProductQueryService;

    @Autowired
    public FranchiseReturnProductQueryController(
            FranchiseReturnProductQueryService franchiseReturnProductQueryService) {
        this.franchiseReturnProductQueryService = franchiseReturnProductQueryService;
    }

    @Operation(
            summary = "담당 가맹점 월 자재별 반품량 통계 조회",
            description = "자신이 담당하는 가맹점들의 월 자재별 반품량을 조회한다.")
    @GetMapping("/manager")
    public ResponseEntity<List<FranchiseReturnProductQueryDTO>> getManagerStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();

        List<FranchiseReturnProductQueryDTO> stats = franchiseReturnProductQueryService.getMonthlyStatsByManager (
                userId);

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "부서별 가맹점 월 자재별 반품량 통계 조회",
            description = "자신이 속한 부서가 담당하는 가맹점들의 월 주문 금액 통계를 조회한다.")
    @GetMapping("/department")
    public ResponseEntity<List<FranchiseReturnProductQueryDTO>> getDepartmentStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long deptId = customUserDetails.getDepartmentId();
        List<FranchiseReturnProductQueryDTO> stats = franchiseReturnProductQueryService.getMonthlyStatsByDepartment (
                deptId);

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "전체 가맹점 월 자재별 반품량 통계 조회",
            description = "부서와 직책에 따라 전체 가맹점 통계를 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<List<FranchiseReturnProductQueryDTO>> getAllStats (
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        Long deptId = customUserDetails.getDepartmentId();
        Long dutyId = customUserDetails.getDutyId();

        List<FranchiseReturnProductQueryDTO> stats
                = franchiseReturnProductQueryService.getMonthlyStatsForAllByDuty(userId, deptId, dutyId);

        return ResponseEntity.ok(stats);
    }
}
