package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.ApprovalListDTO;

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

    List<ApprovalListDTO> getApprovalListReceivedClosed(long userId);

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

    String findLatestApprovalCode(String codePrefix);
}
