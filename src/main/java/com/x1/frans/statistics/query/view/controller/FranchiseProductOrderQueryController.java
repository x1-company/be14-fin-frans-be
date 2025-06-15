package com.x1.frans.statistics.query.view.controller;

import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;
import com.x1.frans.statistics.query.view.service.FranchiseProductOrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
