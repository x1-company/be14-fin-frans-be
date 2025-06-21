package com.x1.frans.approval.query.controller;

import com.x1.frans.approval.query.dto.ApprovalLineTemplateDTO;
import com.x1.frans.approval.query.dto.ApprovalLineTemplateDetailDTO;
import com.x1.frans.approval.query.dto.ApprovalReceivedListDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 결재 상신 관련

    @Operation(summary = "결재 목록 조회 - 상신 전체문서", description = "상신된 전체 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListSubmittedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListSubmittedAll(userId);

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

    // 결재 수신 관련

    @Operation(summary = "결재 목록 조회 - 수신받은 전체문서", description = "수신된 전체 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 수신받은 전체 결재문서", description = "수신된 전체 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/approval/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedApprovalAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedApprovalAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재 대기", description = "결재 대기 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/pending")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedPending(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedPending(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재 예정", description = "결재 예정 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/upcoming")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedUpcoming(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedUpcoming(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재자 결재 완료 전체 문서", description = "결재자의 결재 완료 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/my-completed/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedMyCompletedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedMyCompletedAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재자 승인 및 결재 완료문서", description = "결재자 승인 및 종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/my-completed/approved")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedMyCompletedApproved(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedMyCompletedApproved(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재자 반려 및 결재 완료문서", description = "결재자 반려 및 종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/my-completed/rejected")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedMyCompletedRejected(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedMyCompletedRejected(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 종료된 문서 전체", description = "종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 종료된 문서 전체 결재 문서", description = "종료된 결재문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/approval/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedApprovalAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedApprovalAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재자 승인 및 종료된 문서", description = "결재자 승인 및 종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/approver/approved")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedApproverApproved(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedApproverApproved(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 결재자 반려 및 종료된 문서", description = "결재자 반려 및 종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/approver/rejected")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedApproverRejected(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedApproverRejected(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 종료된 문서 전체 협조 문서", description = "종료된 문서 협조 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/cooperator/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedCooperatorAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedCooperatorAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조자 승인 및 종료된 문서", description = "협조자 승인 및 종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/cooperator/approved")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedCooperatorApproved(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedCooperatorApproved(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조자 반려 및 종료된 문서", description = "협조자 반려 및 종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/cooperator/rejected")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedCooperatorRejected(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedCooperatorRejected(userId);

        return ResponseEntity.ok(list);
    }


    @Operation(summary = "결재 목록 조회 - 전체 협조문서", description = "전체 협조문서 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperateAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperateAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조 대기", description = "협조 대기 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/pending")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperatePending(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperatePending(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조 예정", description = "협조 예정 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/upcoming")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperateUpcoming(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperateUpcoming(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 내 협조 완료 전체", description = "내 협조 완료 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/my-completed/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperateMyCompletedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperateMyCompletedAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조 승인", description = "협조 승인 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/approved")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperateApproved(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperateApproved(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조 반려", description = "협조 반려 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/rejected")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperateRejected(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperateRejected(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 참조문서 ", description = "참조문서 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/references")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReferences(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReferences(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 수신문서 ", description = "수신문서 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/notifications")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListNotifications(userId);

        return ResponseEntity.ok(list);
    }


    @Operation(summary = "결재 상세 조회 - 결재 본문", description = "결재 상세본문 조회한다.")
    @GetMapping("/detail/{approvalId}/content")
    public ResponseEntity<List<ApprovalContentDTO>> getApprovalDetailContent(@AuthenticationPrincipal CustomUserDetails user,
                                                                             @PathVariable long approvalId) {

        long userId = user.getUserId();
        List<ApprovalContentDTO> list = approvalQueryService.getApprovalDetailContent(userId,approvalId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 상세 조회 - 결재선", description = "결재 상세 결재선 조회한다.")
    @GetMapping("/detail/{approvalId}/lines")
    public ResponseEntity<ApprovalLinesDTO> getApprovalDetailLines(@PathVariable long approvalId) {

        ApprovalLinesDTO list = approvalQueryService.getApprovalDetailLines(approvalId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 템플릿 목록 조회", description = "결재 템플릿 목록 조회할 수 있다.")
    @GetMapping("/templates")
    public ResponseEntity<List<ApprovalLineTemplateDTO>> getApprovalLineTemplates(@AuthenticationPrincipal CustomUserDetails user) {

        long userId = user.getUserId();
        List<ApprovalLineTemplateDTO> list = approvalQueryService.getApprovalLineTemplates(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 템플릿 상세 조회", description = "결재 템플릿 상세 조회할 수 있다.")
    @GetMapping("/templates/{templateId}")
    public ResponseEntity<List<ApprovalLineTemplateDetailDTO>> getApprovalLineDetailTemplates(@AuthenticationPrincipal CustomUserDetails user,
                                                                                              @PathVariable long templateId) {

        long userId = user.getUserId();
        List<ApprovalLineTemplateDetailDTO> list = approvalQueryService.getApprovalLineDetailTemplates(userId, templateId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 중인 수신받은 문서 목록조회", description = "결재 중인 수신받은 문서 목록 조회할 수 있다.")
    @GetMapping("/list/received/in-progress")
    public ResponseEntity<List<ApprovalReceivedListDTO>> getApprovalListReceivedInProgress(@AuthenticationPrincipal CustomUserDetails user) {

        long userId = user.getUserId();
        List<ApprovalReceivedListDTO> list = approvalQueryService.getApprovalListReceivedInProgress(userId);

        return ResponseEntity.ok(list);
    }
    @Operation(summary = "결재 완료인 수신받은 문서 목록조회", description = "결재 완료인 수신받은 문서 목록 조회할 수 있다.")
    @GetMapping("/list/received/approved")
    public ResponseEntity<List<ApprovalReceivedListDTO>> getApprovalListReceivedApproved(@AuthenticationPrincipal CustomUserDetails user) {

        long userId = user.getUserId();
        List<ApprovalReceivedListDTO> list = approvalQueryService.getApprovalListReceivedApproved(userId);

        return ResponseEntity.ok(list);
    }
    @Operation(summary = "결재 반려인 수신받은 문서 목록조회", description = "결재 반려인 수신받은 문서 목록조회 할 수 있다.")
    @GetMapping("/list/received/rejected")
    public ResponseEntity<List<ApprovalReceivedListDTO>> getApprovalListReceivedRejected(@AuthenticationPrincipal CustomUserDetails user) {

        long userId = user.getUserId();
        List<ApprovalReceivedListDTO> list = approvalQueryService.getApprovalListReceivedRejected(userId);

        return ResponseEntity.ok(list);
    }








}
