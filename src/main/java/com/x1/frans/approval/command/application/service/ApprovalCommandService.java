package com.x1.frans.approval.command.application.service;

import com.x1.frans.approval.command.application.dto.*;

import java.util.Optional;

public interface ApprovalCommandService {
    ApprovalResponseDTO createApproval(ApprovalCreateRequestDTO request, long userId);

    Optional<ApprovalResponseDTO> approverApprove(ApprovalApproveRequestDTO request, long approvalId, long userId);

    Optional<ApprovalResponseDTO> approverReject(ApprovalRejectRequestDTO request, long approvalId, long userId);

    Optional<ApprovalResponseDTO> approvalLineTemplates(ApprovalLineTemplateCreateRequestDTO request, long userId);

    Optional<ApprovalResponseDTO> approvalLineTemplatesModify(ApprovalLineTemplateCreateRequestDTO request, long userId, Long templateId);
}
