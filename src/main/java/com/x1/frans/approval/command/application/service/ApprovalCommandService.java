package com.x1.frans.approval.command.application.service;

import com.x1.frans.approval.command.application.dto.*;

import java.util.Optional;

public interface ApprovalCommandService {
    ApprovalResponseDTO createApproval(ApprovalCreateRequestDTO request, long userId);

    Optional<ApprovalResponseDTO> approverApprove(ApprovalApproveRequestDTO request, long approvalId, long userId);

    Optional<ApprovalResponseDTO> approverReject(ApprovalRejectRequestDTO request, long approvalId, long userId);

    Optional<ApprovalResponseDTO> approvalLineTemplates(ApprovalLineTemplateCreateRequestDTO request, long userId);

    Optional<ApprovalResponseDTO> approvalLineTemplatesModify(ApprovalLineTemplateModifyRequestDTO request, long userId, Long templateId);

    Optional<ApprovalResponseDTO> deleteApprovalLineTemplates(long userId, Long templateId);

    ApprovalResponseDTO modifyApproval(ApprovalCreateRequestDTO request, long userId, long approvalId);

    ApprovalResponseDTO updateRequestState(ApprovalStatusUpdateDTO request, long userId, long approvalId);

    ApprovalResponseDTO requestApproval(long userId, long approvalId);

    void approvalLineTemplatesSeqModify(long userId, Long templateId, int seq);

    ApprovalResponseDTO createApprovalDrafts(ApprovalDraftCreateRequestDTO request, long userId);

    ApprovalResponseDTO deleteApprovalDrafts(Long approvalId, long userId);
}
