package com.x1.frans.approval.query.controller;

import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.security.CustomUserDetails;
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
@Slf4j
public class ApprovalQueryController {

    private final ApprovalQueryService approvalQueryService;

    public ApprovalQueryController(ApprovalQueryService approvalQueryService) {
        this.approvalQueryService = approvalQueryService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ApprovalListDTO>> getApprovalList(@AuthenticationPrincipal CustomUserDetails user) {
        long userId = user.getUserId();
        List<ApprovalListDTO> list = approvalQueryService.getApprovalList(userId);
        return ResponseEntity.ok(list);

    }
}
