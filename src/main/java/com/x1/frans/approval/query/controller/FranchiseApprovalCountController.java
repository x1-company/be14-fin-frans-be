package com.x1.frans.approval.query.controller;

import com.x1.frans.approval.query.dto.FranchiseApprovedApprovalCountDTO;
import com.x1.frans.approval.query.dto.FranchiseApprovalCountDTO;
import com.x1.frans.approval.query.service.FranchiseApprovalCountService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "가맹점 결재 개수", description = "가맹점주 진행중 결재 개수 API")
@RestController
@RequestMapping("/api/hq/franchise/approval")
public class FranchiseApprovalCountController {

    private final FranchiseApprovalCountService service;

    public FranchiseApprovalCountController(FranchiseApprovalCountService service) {
        this.service = service;
    }

    @Operation(summary = "진행중 결재 개수 조회", description = "가맹점주가 기안한 주문/반품 결재 중 진행중인 건수 조회")
    @GetMapping("/in-progress/count")
    public ResponseEntity<FranchiseApprovalCountDTO> getInProgressApprovalCounts(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        FranchiseApprovalCountDTO dto = service.getInProgressApprovalCounts(userId);

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "완료된 결재 개수 조회", description = "가맹점주가 기안한 주문 결재 중 완료된 건수 조회")
    @GetMapping("/approved/count")
    public ResponseEntity<FranchiseApprovedApprovalCountDTO> getCompletedApprovalCounts(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        FranchiseApprovedApprovalCountDTO dto = service.getApprovedApprovalCounts(userId);
        return ResponseEntity.ok(dto);
    }

}