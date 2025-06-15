package com.x1.frans.returns.query.controller;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.returns.query.dto.ReturnSearchConditionDTO;
import com.x1.frans.returns.query.dto.ReturnSearchPageDTO;
import com.x1.frans.returns.query.service.ReturnQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "🔄 반품", description = "반품 관련 API")
@RestController
@RequestMapping("/api/hq/franchise/return")
public class HqReturnQueryController {

    private final ReturnQueryService returnQueryService;

    public HqReturnQueryController(ReturnQueryService returnQueryService) {
        this.returnQueryService = returnQueryService;
    }

    @GetMapping
    @Operation(summary = "반품 목록 조회",
                description = "본인이 속한 부서 및 본인이 관리하는 가맹점의 반품 목록을 전체 조회할 수 있다")
    public ResponseEntity<ReturnSearchPageDTO> findAllReturns(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute ReturnSearchConditionDTO condition) {

        Long userId = userDetails.getUserId();

        ReturnSearchPageDTO list = returnQueryService.findAllReturns(userId, condition);

        return ResponseEntity.ok(list);
    }
}
