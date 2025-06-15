package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;
import com.x1.frans.approval.query.repository.ApprovalQueryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApprovalQueryServiceImpl implements ApprovalQueryService {

    private final ApprovalQueryMapper approvalQueryMapper;

    @Autowired
    public ApprovalQueryServiceImpl(ApprovalQueryMapper approvalQueryMapper) {
        this.approvalQueryMapper = approvalQueryMapper;
    }

    @Override
    public List<ApprovalListDTO> getApprovalListSubmittedAll(long userId) {
        return approvalQueryMapper.getApprovalListSubmittedAll(userId);
    }


    @Override
    public List<ApprovalListDTO> getApprovalListDraft(long userId) {
        return approvalQueryMapper.getApprovalListDraft(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListInProgress(long userId) {
        return approvalQueryMapper.getApprovalListInProgress(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListApproved(long userId) {
        return approvalQueryMapper.getApprovalListApproved(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListRejected(long userId) {
        return approvalQueryMapper.getApprovalListRejected(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedAll(long userId) {
        return approvalQueryMapper.getApprovalListReceivedAll(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedPending(long userId) {
        return approvalQueryMapper.getApprovalListReceivedPending(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedUpcoming(long userId) {
        return approvalQueryMapper.getApprovalListReceivedUpcoming(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedMyCompletedAll(long userId) {
        return approvalQueryMapper.getApprovalListReceivedMyCompletedAll(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosed(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosed(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedMyCompletedApproved(long userId) {
        return approvalQueryMapper.getApprovalListReceivedMyCompletedApproved(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedMyCompletedRejected(long userId) {
        return approvalQueryMapper.getApprovalListReceivedMyCompletedRejected(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosedApproverApproved(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedApproverApproved(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosedApproverRejected(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedApproverRejected(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorApproved(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedCooperatorApproved(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorRejected(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedCooperatorRejected(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListCooperateAll(long userId) {
        return approvalQueryMapper.getApprovalListCooperateAll(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListCooperatePending(long userId) {
        return approvalQueryMapper.getApprovalListCooperatePending(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListCooperateApproved(long userId) {
        return approvalQueryMapper.getApprovalListCooperateApproved(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListCooperateRejected(long userId) {
        return approvalQueryMapper.getApprovalListCooperateRejected(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReferences(long userId) {
        return approvalQueryMapper.getApprovalListReferences(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListNotifications(long userId) {
        return approvalQueryMapper.getApprovalListNotifications(userId);
    }

    @Override
    public List<ApprovalContentDTO> getApprovalDetailContent(Long userId, long approvalId) {

        // ORDER, RETURN, PURCHASE_ORDER
        String category = approvalQueryMapper.findCategoryByApprovalId(approvalId);

        return switch (category) {
            case "ORDER" -> approvalQueryMapper.findOrderContent(approvalId,userId);
            case "RETURN" -> approvalQueryMapper.findReturnContent(approvalId,userId);
            case "PURCHASE_ORDER" -> approvalQueryMapper.findPurchaseOrderContent(approvalId,userId);
            default -> throw new IllegalArgumentException("결재 유형을 판단할 수 없습니다.");
        };

    }

    @Override
    public List<ApprovalLinesDTO> getApprovalDetailLines(long approvalId) {
        return approvalQueryMapper.findApprovalDetailLines(approvalId);
    }

    @Override
    public String findLatestApprovalCode(String codePrefix) {
        return approvalQueryMapper.findLatestApprovalCode(codePrefix);
    }
}




