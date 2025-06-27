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
import com.x1.frans.exception.ApprovalNotFoundException;
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
        // 기본 임시저장 데이터 조회
        ApprovalDraftDTO draft = approvalQueryMapper.selectApprovalDraft(approvalId);

        // Null 체크 (없으면 예외 던지기)
        if (draft == null) {
            throw new ApprovalNotFoundException("임시저장 상태의 결재문서를 찾을 수 없습니다. (approvalId: " + approvalId + ")");
        }

        //  결재선, 파일 조회
        List<ApprovalDraftLineDTO> lines = approvalQueryMapper.selectApprovalDraftLines(approvalId);
        List<ApprovalFileDTO> files = approvalQueryMapper.selectApprovalDraftFiles(approvalId);

        // categoryType 조회
        String categoryType = approvalQueryMapper.selectApprovalCategoryType(approvalId);
        if (categoryType == null) {
            throw new IllegalStateException("categoryType이 null입니다. approvalId: " + approvalId);
        }
        // documentIds 조회 (categoryType별)
//        List<Long> documentIds;
//        switch (categoryType) {
//            case "ORDER" -> documentIds = approvalQueryMapper.selectApprovalDocumentIds(approvalId);
//            case "RETURN" -> documentIds = approvalQueryMapper.selectApprovalDocumentIdsReturn(approvalId);
//            case "PURCHASE_ORDER" -> documentIds = approvalQueryMapper.selectApprovalDocumentIdsPurchase(approvalId);
//            default -> throw new IllegalArgumentException("Unknown categoryType: " + categoryType);
//        }

        //  ApprovalDocumentDTO 조회
        List<ApprovalDocumentDTO> docDto;
        switch (categoryType) {
            case "ORDER" -> docDto = approvalQueryMapper.selectApprovalDocumentMetaOrder(approvalId);
            case "RETURN" -> docDto = approvalQueryMapper.selectApprovalDocumentMetaReturn(approvalId);
            case "PURCHASE_ORDER" -> docDto = approvalQueryMapper.selectApprovalDocumentMetaPurchase(approvalId);
            default -> throw new IllegalArgumentException("Unknown categoryType: " + categoryType);
        }
        // 최종 세팅
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




