package com.x1.frans.approval.command.application.service;

import com.x1.frans.approval.command.application.dto.ApprovalDecisionRequestDTO;
import com.x1.frans.approval.command.application.dto.ApprovalResponseDTO;
import com.x1.frans.approval.command.application.dto.ApprovalCreateRequestDTO;

import java.util.Optional;

public interface ApprovalCommandService {
    ApprovalResponseDTO createApproval(ApprovalCreateRequestDTO request, long userId);

    Optional<ApprovalResponseDTO> approverApprove(ApprovalDecisionRequestDTO request, long approvalId, long userId);

    Optional<ApprovalResponseDTO> approverReject(ApprovalDecisionRequestDTO request, long approvalId, long userId);
}
