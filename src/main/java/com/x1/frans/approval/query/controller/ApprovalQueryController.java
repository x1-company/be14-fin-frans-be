package com.x1.frans.approval.query.controller;

import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "결재", description = "결재 관련 API")
@RequestMapping("/api/hq/approvals")
@RestController
public class ApprovalQueryController {

    private final ApprovalQueryService approvalQueryService;

    public ApprovalQueryController(ApprovalQueryService approvalQueryService) {
        this.approvalQueryService = approvalQueryService;
    }

    @Operation(summary = "결재 목록 조회 - 상신 전체", description = "상신 전체 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListSubmitted(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListSubmitted(userId);

        return ResponseEntity.ok(list);
    }


    @Operation(summary = "결재 목록 조회 - 임시저장", description = "임시저장된 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted/draft")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListDrafts(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListDraft(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재중", description = "결재중인 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted/in-progress")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListInProgress(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListInProgress(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재완료", description = "결재완료된 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted/approved")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListApproved(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListApproved(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재반려", description = "결재반려된 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted/rejected")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListRejected(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListRejected(userId);

        return ResponseEntity.ok(list);
    }

}
