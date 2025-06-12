package com.x1.frans.approval.query.controller;

import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "결재", description = "결재 관련 API")
@RequestMapping("/api/hq/approval")
@RestController
public class ApprovalQueryController {

    private final ApprovalQueryService approvalQueryService;

    public ApprovalQueryController(ApprovalQueryService approvalQueryService) {
        this.approvalQueryService = approvalQueryService;
    }

    @Operation(summary = "결재 목록 조회 - 최신순", description = "전체 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/newest")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListNewest(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListNewest(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 오래된순", description = "전체 결재 리스트를 오래된순으로 조회한다.")
    @GetMapping("/list/oldest")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListOldest(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListOldest(userId);

        return ResponseEntity.ok(list);
    }


}
