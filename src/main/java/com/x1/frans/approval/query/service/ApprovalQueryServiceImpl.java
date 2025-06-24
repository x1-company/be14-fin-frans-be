package com.x1.frans.approval.query.service;

import com.x1.frans.approval.command.domain.repository.ApprovalLineCommandRepository;
import com.x1.frans.approval.query.dto.*;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalFileDTO;
import com.x1.frans.approval.query.dto.Detail.content.OrderReturn.ApprovalOrderReturnContentDTO;
import com.x1.frans.approval.query.dto.Detail.content.PurchaseOrder.ApprovalPurchaseOrderContentDTO;
import com.x1.frans.approval.query.dto.Detail.content.PurchaseOrder.ApprovalPurchaseOrderHistoryDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;
import com.x1.frans.approval.query.repository.ApprovalQueryMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ApprovalQueryServiceImpl implements ApprovalQueryService {

    private final ApprovalQueryMapper approvalQueryMapper;
    private final ApprovalLineCommandRepository approvalLineCommandRepository;

    @Autowired
    public ApprovalQueryServiceImpl(ApprovalQueryMapper approvalQueryMapper, ApprovalLineCommandRepository approvalLineCommandRepository) {
        this.approvalQueryMapper = approvalQueryMapper;
        this.approvalLineCommandRepository = approvalLineCommandRepository;
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
    public List<ApprovalListDTO> getApprovalListReceivedClosedAll(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedAll(userId);
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

    @Transactional
    @Override
    public ApprovalContentDTO getApprovalDetailContent(Long userId, long approvalId) {

        // category 구함
        String category = approvalQueryMapper.findCategoryByApprovalId(approvalId);

        ApprovalContentDTO content = switch (category) {
            case "ORDER" -> approvalQueryMapper.findOrderContent(approvalId, userId);
            case "RETURN" -> approvalQueryMapper.findReturnContent(approvalId, userId);
            case "PURCHASE_ORDER" -> approvalQueryMapper.findPurchaseOrderContent(approvalId, userId);
            default -> throw new IllegalArgumentException("결재 유형을 판단할 수 없습니다.");
        };

        // totalAmount 쿼리
        BigDecimal totalAmount = switch (category) {
            case "ORDER" -> approvalQueryMapper.findTotalOrderAmountByApprovalId(approvalId);
            case "RETURN" -> approvalQueryMapper.findTotalReturnAmountByApprovalId(approvalId);
            case "PURCHASE_ORDER" -> approvalQueryMapper.findTotalPurchaseOrderAmountByApprovalId(approvalId);
            default -> throw new IllegalArgumentException("결재 유형을 판단할 수 없습니다.");
        };

        // 읽음 처리
        approvalLineCommandRepository.markAsChecked(approvalId, userId);

        // set
        content.setTotalAmount(totalAmount);

        return content;
    }

    @Override
    public ApprovalLinesDTO getApprovalDetailLines(long approvalId) {
        return approvalQueryMapper.getApprovalDetailLines(approvalId).get(0);
    }

    @Override
    public String findLatestApprovalCode(String codePrefix) {
        return approvalQueryMapper.findLatestApprovalCode(codePrefix);
    }

    @Override
    public List<ApprovalLineTemplateDTO> getApprovalLineTemplates(long userId) {
        return approvalQueryMapper.getApprovalLineTemplates(userId);
    }

    @Override
    public List<ApprovalLineTemplateDetailDTO> getApprovalLineDetailTemplates(long userId, long templateId) {
        return approvalQueryMapper.getApprovalLineDetailTemplates(userId, templateId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedApprovalAll(long userId) {
        return approvalQueryMapper.getApprovalListReceivedApprovalAll(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosedApprovalAll(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedApprovalAll(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorAll(long userId) {
        return approvalQueryMapper.getApprovalListReceivedClosedCooperatorAll(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListCooperateUpcoming(long userId) {
        return approvalQueryMapper.getApprovalListCooperateUpcoming(userId);

    }

    @Override
    public List<ApprovalListDTO> getApprovalListCooperateMyCompletedAll(long userId) {
        return approvalQueryMapper.getApprovalListCooperateMyCompletedAll(userId);
    }

    @Override
    public ApprovalDraftDTO getApprovalDraft(long approvalId) {
        ApprovalDraftDTO draft = approvalQueryMapper.selectApprovalDraft(approvalId);
        List<ApprovalDraftLineDTO> lines = approvalQueryMapper.selectApprovalDraftLines(approvalId);
        List<ApprovalFileDTO> files = approvalQueryMapper.selectApprovalDraftFiles(approvalId);

        String categoryType = approvalQueryMapper.selectApprovalCategoryType(approvalId);

        List<Long> documentIds;
        switch (categoryType) {
            case "ORDER" -> documentIds = approvalQueryMapper.selectApprovalDocumentIds(approvalId);  // order_approval
            case "RETURN" -> documentIds = approvalQueryMapper.selectApprovalDocumentIdsReturn(approvalId);
            case "PURCHASE_ORDER" -> documentIds = approvalQueryMapper.selectApprovalDocumentIdsPurchase(approvalId);
            default -> throw new IllegalArgumentException("Unknown categoryType");
        }

        ApprovalDocumentDTO docDto = new ApprovalDocumentDTO(categoryType, documentIds);
        draft.setApprovalDocuments(docDto);
        draft.setLines(lines);
        draft.setFiles(files);
        draft.setCategoryType(categoryType);

        return draft;
    }

    @Override
    public List<ApprovalReceivedListDTO> getApprovalListReceivedInProgress(long userId) {
        return approvalQueryMapper.getApprovalListReceivedInProgress(userId);
    }

    @Override
    public List<ApprovalReceivedListDTO> getApprovalListReceivedApproved(long userId) {
        return approvalQueryMapper.getApprovalListReceivedApproved(userId);
    }

    @Override
    public List<ApprovalReceivedListDTO> getApprovalListReceivedRejected(long userId) {
        return approvalQueryMapper.getApprovalListReceivedRejected(userId);
    }
}




