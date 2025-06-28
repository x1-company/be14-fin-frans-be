package com.x1.frans.approval.query.controller;

import com.x1.frans.approval.common.ApprovalLineStatus;
import com.x1.frans.approval.query.dto.*;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "결재 상신 목록 전체 조회", description = "상신된 전체 결재 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListSubmittedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListSubmittedAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 상신 목록 조회( DRAFT, IN_PROGRESS, APPROVED, REJECTED )", description = "상신한 결재 상태별 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/submitted")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListByStatus(@AuthenticationPrincipal CustomUserDetails user,
                                                                         @RequestParam(required = false) String status) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListByStatus(userId, status);

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

    @Operation(summary = "결재 목록 조회 - 수신함( WAITING, EXPECTED )", description = "결재자 수신함 리스트를 상태별로 최신순 조회한다.")
    @GetMapping("/list/received")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceived(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "status") ApprovalLineStatus status
    ) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceived(userId, status);

        return ResponseEntity.ok(list);
    }


    @Operation(summary = "결재 목록 조회 - 결재자가 완료한 문서( APPROVED, REJECTED )", description = "결재자가 승인 또는 반려로 완료한 문서를 상태에 따라 조회한다.")
    @GetMapping("/list/received/my-completed")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedMyCompleted(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "status") ApprovalLineStatus status
    ) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedMyCompleted(userId, status);

        return ResponseEntity.ok(list);
    }


    @Operation(summary = "결재 목록 조회 - 종료된 문서 전체", description = "종료된 문서 전체 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/received/closed/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListReceivedClosedAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListReceivedClosedAll(userId);

        return ResponseEntity.ok(list);
    }

    // 보류 - 여기서부터
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
    // 여기까지

    // 협조문서

    @Operation(summary = "결재 목록 조회 - 전체 협조문서", description = "전체 협조문서 리스트를 최신순으로 조회한다.")
    @GetMapping("/list/cooperate/all")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperateAll(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperateAll(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "결재 목록 조회 - 협조 문서", description = "협조 문서를 상태별로 조회한다. (WAITING, EXPECTED, APPROVED, REJECTED)")
    @GetMapping("/list/cooperate")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalListCooperate(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(name = "status") ApprovalLineStatus status
    ) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalListCooperate(userId, status);
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
    public ResponseEntity<ApprovalContentDTO> getApprovalDetailContent(@AuthenticationPrincipal CustomUserDetails user,
                                                                             @PathVariable long approvalId) {

        long userId = user.getUserId();
        ApprovalContentDTO list = approvalQueryService.getApprovalDetailContent(userId,approvalId);

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
    @Operation(summary = "임시저장한 문서 상세조회", description = "임시저장한 문서 상세조회 할 수 있다.")
    @GetMapping("/draft/{approvalId}")
    public ResponseEntity<ApprovalDraftDTO> getApprovalDraft(@PathVariable long approvalId) {

        ApprovalDraftDTO list = approvalQueryService.getApprovalDraft(approvalId, false);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "재기안 할 문서 상세조회", description = "재기안 할 문서 상세조회 할 수 있다.")
    @GetMapping("/re-draft/{approvalId}")
    public ResponseEntity<ApprovalDraftDTO> getApprovalReDraft(@PathVariable long approvalId) {

        ApprovalDraftDTO list = approvalQueryService.getApprovalDraft(approvalId, true);

        return ResponseEntity.ok(list);
    }








}
