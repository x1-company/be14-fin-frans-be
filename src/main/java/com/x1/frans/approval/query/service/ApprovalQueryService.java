package com.x1.frans.approval.query.service;

import com.x1.frans.approval.common.ApprovalLineStatus;
import com.x1.frans.approval.query.dto.*;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;

import java.util.List;

public interface ApprovalQueryService {
    List<ApprovalListDTO> getApprovalListSubmittedAll(long userId);


    List<ApprovalListDTO> getApprovalListReceivedAll(long userId);


    List<ApprovalListDTO> getApprovalListReceivedClosedAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedMyCompletedApproved(long userId);

    List<ApprovalListDTO> getApprovalListReceivedMyCompletedRejected(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedApproverApproved(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedApproverRejected(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorApproved(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorRejected(long userId);

    List<ApprovalListDTO> getApprovalListCooperateAll(long userId);

    List<ApprovalListDTO> getApprovalListCooperatePending(long userId);

    List<ApprovalListDTO> getApprovalListCooperateApproved(long userId);

    List<ApprovalListDTO> getApprovalListCooperateRejected(long userId);

    List<ApprovalListDTO> getApprovalListReferences(long userId);

    List<ApprovalListDTO> getApprovalListNotifications(long userId);

    ApprovalContentDTO getApprovalDetailContent(Long userId,long approvalId);

    ApprovalLinesDTO getApprovalDetailLines(long approvalId);
  
    String findLatestApprovalCode(String codePrefix);

    List<ApprovalLineTemplateDTO> getApprovalLineTemplates(long userId);

    List<ApprovalLineTemplateDetailDTO> getApprovalLineDetailTemplates(long userId, long templateId);



    List<ApprovalListDTO> getApprovalListReceivedApprovalAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedApprovalAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorAll(long userId);

    List<ApprovalListDTO> getApprovalListCooperateUpcoming(long userId);

    List<ApprovalListDTO> getApprovalListCooperateMyCompletedAll(long userId);

    List<ApprovalReceivedListDTO> getApprovalListReceivedInProgress(long userId);

    List<ApprovalReceivedListDTO> getApprovalListReceivedApproved(long userId);

    List<ApprovalReceivedListDTO> getApprovalListReceivedRejected(long userId);

    ApprovalDraftDTO getApprovalDraft(long approvalId, boolean allowDraft);

    // 결재 상신 목록
    List<ApprovalListDTO> getApprovalListByStatus(long userId, String status);


    List<ApprovalListDTO> getApprovalListReceived(long userId, ApprovalLineStatus status);

    List<ApprovalListDTO> getApprovalListReceivedMyCompleted(long userId, ApprovalLineStatus status);

    List<ApprovalListDTO> getApprovalListCooperate(long userId, ApprovalLineStatus status);
}
