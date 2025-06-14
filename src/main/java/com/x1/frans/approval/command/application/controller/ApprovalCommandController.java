package com.x1.frans.approval.command.application.controller;

import com.x1.frans.approval.command.application.dto.ApprovalCreateResponseDTO;
import com.x1.frans.approval.command.application.dto.ApprovalCreateRequestDTO;
import com.x1.frans.approval.command.application.service.ApprovalCommandService;
import com.x1.frans.approval.command.application.dto.CommonResponse;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CommonResponse<ApprovalCreateResponseDTO>> createApproval(@RequestBody ApprovalCreateRequestDTO request,
                                                                                    @AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();

        ApprovalCreateResponseDTO responseDTO = approvalCommandService.createApproval(request,userId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(responseDTO, "결재 등록이 완료되었습니다."));

    }
}
