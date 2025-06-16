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
    public ResponseEntity<CommonResponse<ApprovalResponseDTO>> approvalLineTemplatesModify(@RequestBody ApprovalLineTemplateCreateRequestDTO request,
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
    @Operation(summary = "결재 수정", description = "결재를 수정합니다.")
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



}
