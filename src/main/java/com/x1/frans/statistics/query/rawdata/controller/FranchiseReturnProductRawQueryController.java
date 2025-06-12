package com.x1.frans.statistics.query.rawdata.controller;

import com.x1.frans.statistics.query.rawdata.dto.FranchiseOrderAmountRawDTO;
import com.x1.frans.statistics.query.rawdata.dto.FranchiseReturnProductRawDTO;
import com.x1.frans.statistics.query.rawdata.service.FranchiseReturnProductRawQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "원천 데이터 조회", description = "스케줄러가 통계 생성에 사용할 원천 데이터를 조회하는 기능")
@Slf4j
@RestController
@RequestMapping("/api/statistics/rawdata")
public class FranchiseReturnProductRawQueryController {

    private final FranchiseReturnProductRawQueryService franchiseReturnProductRawQueryService;

    @Autowired
    public FranchiseReturnProductRawQueryController(
            FranchiseReturnProductRawQueryService franchiseReturnProductRawQueryService) {
        this.franchiseReturnProductRawQueryService = franchiseReturnProductRawQueryService;
    }

    @Operation(summary = "원천 데이터 조회", description = "정해진 기간으로 반품량 통계를 위한 원천 데이터를 조회한다.")
    @GetMapping("/return-product")
    public ResponseEntity<List<FranchiseReturnProductRawDTO>> findTotalReturnProductByFranchise(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(franchiseReturnProductRawQueryService.getReturnProducts(from, to));
    }
}
