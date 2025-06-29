package com.x1.frans.approval.command.application.controller;

import com.x1.frans.approval.command.application.dto.*;
import com.x1.frans.approval.command.application.service.ApprovalCommandService;
import com.x1.frans.approval.common.CommonResponse;
import com.x1.frans.exception.ApprovalActionFailedException;
import com.x1.frans.exception.ApprovalLineTemplateActionFailedException;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "결재", description = "결재 관련 API")
@RequestMapping("/api/hq/approvals")
@RestController
public class ApprovalCommandController {

    private final ApprovalCommandService approvalCommandService;

    @Autowired
    public ApprovalCommandController(ApprovalCommandService approvalCommandService) {
        this.approvalCommandService = approvalCommandService;
    }

    @Operation(summary = "결재 등록", description = "결재를 등록합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> createApproval(@RequestBody ApprovalCreateRequestDTO request,
                                                                              @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();

        ApprovalResponseDTO responseDTO = approvalCommandService.createApproval(request,userId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재 등록이 완료되었습니다."));

    }

    // 보류
    @Operation(summary = "결재 임시저장", description = "결재를 임시저장합니다.")
    @PostMapping("/drafts")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> createApprovalDrafts(@RequestBody ApprovalDraftCreateRequestDTO request,
                                                                              @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();

        ApprovalResponseDTO responseDTO = approvalCommandService.createApprovalDrafts(request,userId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "임시저장이 완료되었습니다."));

    }


    @Operation(summary = "결재자/협조자 승인", description = "결재자/협조자가 결재를 승인합니다.")
    @PostMapping("/{approvalId}/approve")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> approverApprove(@RequestBody ApprovalApproveRequestDTO request,
                                                                                      @PathVariable long approvalId,
                                                                                      @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();


        ApprovalResponseDTO responseDTO = approvalCommandService
                .approverApprove(request, approvalId, userId)
                .orElseThrow(() -> new ApprovalActionFailedException("결재 승인 처리 실패"));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "승인 처리되었습니다."));

    }

    @Operation(summary = "결재자/협조자 반려", description = "결재자/협조자가 결재를 반려합니다.")
    @PostMapping("/{approvalId}/reject")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> approverReject(@RequestBody ApprovalRejectRequestDTO request,
                                                                               @PathVariable long approvalId,
                                                                               @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();


        ApprovalResponseDTO responseDTO = approvalCommandService
                .approverReject(request, approvalId, userId)
                .orElseThrow(() -> new ApprovalActionFailedException("결재 반려 처리 실패"));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "반려 처리되었습니다."));

    }


    @Operation(summary = "결재선 템플릿 등록", description = "결재선 템플릿 등록합니다.")
    @PostMapping("/templates")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> approvalLineTemplates(@RequestBody ApprovalLineTemplateCreateRequestDTO request,
                                                                              @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();


        ApprovalResponseDTO responseDTO = approvalCommandService
                .approvalLineTemplates(request, userId)
                .orElseThrow(() -> new ApprovalLineTemplateActionFailedException("결재선 템플릿 등록을 실패했습니다."));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재선 템플릿이 등록되었습니다."));

    }
    @Operation(summary = "결재선 템플릿 수정", description = "결재선 템플릿 수정합니다.")
    @PutMapping("/templates/{templateId}")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> approvalLineTemplatesModify(@RequestBody ApprovalLineTemplateModifyRequestDTO request,
                                                                                     @AuthenticationPrincipal CustomUserDetails user,
                                                                                           @PathVariable Long templateId) {
        long userId = user.getUserId();


        ApprovalResponseDTO responseDTO = approvalCommandService
                .approvalLineTemplatesModify(request, userId, templateId)
                .orElseThrow(() -> new ApprovalLineTemplateActionFailedException("결재선 템플릿 수정을 실패했습니다."));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재선 템플릿이 수정되었습니다."));

    }

    @Operation(summary = "결재선 템플릿 순서 수정", description = "결재선 템플릿 순서를 수정합니다.")
    @PatchMapping("/templates/{templateId}/seq/{seq}")
    public ResponseEntity<Void> approvalLineTemplatesSeqModify(@AuthenticationPrincipal CustomUserDetails user,
                                                               @PathVariable Long templateId,
                                                               @PathVariable int seq) {
        long userId = user.getUserId();

        approvalCommandService.approvalLineTemplatesSeqModify(userId, templateId, seq);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "결재선 템플릿 삭제", description = "결재선 템플릿 삭제합니다.")
    @DeleteMapping("/templates/{templateId}")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> deleteApprovalLineTemplates(@AuthenticationPrincipal CustomUserDetails user,
                                                                                           @PathVariable Long templateId) {
        long userId = user.getUserId();


        ApprovalResponseDTO responseDTO = approvalCommandService
                .deleteApprovalLineTemplates(userId, templateId)
                .orElseThrow(() -> new ApprovalLineTemplateActionFailedException("결재선 템플릿 삭제를 실패했습니다."));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재선 템플릿이 삭제되었습니다."));

    }
    @Operation(summary = "결재 수정(재기안 포함)", description = "결재를 수정합니다.")
    @PutMapping("/{approvalId}")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> modifyApproval(@RequestBody ApprovalCreateRequestDTO request,
                                                                              @AuthenticationPrincipal CustomUserDetails user,
                                                                              @PathVariable long approvalId) {
        long userId = user.getUserId();

        ApprovalResponseDTO responseDTO = approvalCommandService.modifyApproval(request,userId, approvalId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재 수정이 완료되었습니다."));

    }

    @Operation(summary = " 결재진행 중이던 문서를 임시저장 상태로 변경", description = "결재 중이던 문서를 임시저장상태로 변경합니다.")
    @PatchMapping("/{approvalId}/draft")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> updateRequestedStatus(@RequestBody ApprovalStatusUpdateDTO request,
                                                                              @AuthenticationPrincipal CustomUserDetails user,
                                                                              @PathVariable long approvalId) {
        long userId = user.getUserId();

        ApprovalResponseDTO responseDTO = approvalCommandService.updateRequestState(request,userId, approvalId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "임시저장 상태로 변경되었습니다."));

    }

    @Operation(summary = "임시저장된 문서를 결재등록 요청", description = "임시저장된 문서를 결재등록이 된 상태로 변경합니다.")
    @PatchMapping("/{approvalId}/request")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> requestApproval(@AuthenticationPrincipal CustomUserDetails user,
                                                                                     @PathVariable long approvalId) {
        long userId = user.getUserId();

        ApprovalResponseDTO responseDTO = approvalCommandService.requestApproval(userId, approvalId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재가 등록되었습니다."));

    }


    @Operation(summary = "결재 임시저장 삭제", description = "임시저장된 결재 문서가 삭제합니다.")
    @DeleteMapping("/drafts/{approvalId}")
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> deleteApprovalDrafts(@PathVariable Long approvalId,
                                                                                    @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();

        ApprovalResponseDTO responseDTO = approvalCommandService.deleteApprovalDrafts(approvalId, userId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "임시저장 문서가 삭제되었습니다."));

    }


}
