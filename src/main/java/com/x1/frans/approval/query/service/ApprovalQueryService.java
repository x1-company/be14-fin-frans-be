package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;

import java.util.List;

public interface ApprovalQueryService {
    List<ApprovalListDTO> getApprovalListSubmittedAll(long userId);

    List<ApprovalListDTO> getApprovalListDraft(long userId);

    List<ApprovalListDTO> getApprovalListInProgress(long userId);

    List<ApprovalListDTO> getApprovalListApproved(long userId);

    List<ApprovalListDTO> getApprovalListRejected(long userId);

    List<ApprovalListDTO> getApprovalListReceivedAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedPending(long userId);

    List<ApprovalListDTO> getApprovalListReceivedUpcoming(long userId);

    List<ApprovalListDTO> getApprovalListReceivedMyCompletedAll(long userId);

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

    List<ApprovalContentDTO> getApprovalDetailContent(Long userId,long approvalId);

    ApprovalLinesDTO getApprovalDetailLines(long approvalId);
  
    String findLatestApprovalCode(String codePrefix);

    List<ApprovalLinesDTO> getApprovalLineTemplates(long userId);

    List<ApprovalLinesDTO> getApprovalLineDetailTemplates(long userId, long templateId);



    List<ApprovalListDTO> getApprovalListReceivedApprovalAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedApprovalAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorAll(long userId);

    List<ApprovalListDTO> getApprovalListCooperateUpcoming(long userId);

    List<ApprovalListDTO> getApprovalListCooperateMyCompletedAll(long userId);

}
