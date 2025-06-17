package com.x1.frans.approval.query.repository;

import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.dto.ApprovalListLineDTO;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalContentDTO;
import com.x1.frans.approval.query.dto.Detail.content.ApprovalFileDTO;
import com.x1.frans.approval.query.dto.Detail.content.OrderReturn.ApprovalOrderReturnHistoryDTO;
import com.x1.frans.approval.query.dto.Detail.content.PurchaseOrder.ApprovalPurchaseOrderHistoryDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLineDTO;
import com.x1.frans.approval.query.dto.Detail.lines.ApprovalLinesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalQueryMapper  {

    // 결재 상신 관련
    List<ApprovalListDTO> getApprovalListSubmittedAll(long userId);

    List<ApprovalListDTO> getApprovalListDraft(long userId);

    List<ApprovalListDTO> getApprovalListInProgress(long userId);

    List<ApprovalListDTO> getApprovalListApproved(long userId);

    List<ApprovalListDTO> getApprovalListRejected(long userId);

    // 결재 수신 관련
    List<ApprovalListDTO> getApprovalListReceivedAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedApprovalAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedPending(long userId);

    List<ApprovalListDTO> getApprovalListReceivedUpcoming(long userId);

    List<ApprovalListDTO> getApprovalListReceivedMyCompletedAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedAll(long userId);

    List<ApprovalListDTO> getApprovalListReceivedMyCompletedApproved(long userId);

    List<ApprovalListDTO> getApprovalListReceivedMyCompletedRejected(long userId);

    List<ApprovalListDTO> getApprovalListReceivedClosedApprovalAll(long userId);

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

    List<ApprovalListDTO> getApprovalListReceivedClosedCooperatorAll(long userId);

    List<ApprovalListDTO> getApprovalListCooperateUpcoming(long userId);


    String findCategoryByApprovalId(@Param("approvalId") long approvalId);

    // 발주 조회
    List<ApprovalContentDTO> findPurchaseOrderContent(@Param("approvalId") long approvalId,@Param("userId") Long userId);

    // 반품 조회
    List<ApprovalContentDTO> findReturnContent(@Param("approvalId") long approvalId,@Param("userId") Long userId);

    // 주문 조회
    List<ApprovalContentDTO> findOrderContent(@Param("approvalId") long approvalId,@Param("userId") Long userId);



    // 주문 이력 조회
    List<ApprovalOrderReturnHistoryDTO> findOrderHistoryByApprovalId(@Param("id") Long approvalId);

    // 반품 이력 조회
    List<ApprovalOrderReturnHistoryDTO> findReturnHistoryByApprovalId(@Param("id") Long approvalId);

    // 발주 이력 조회
    List<ApprovalPurchaseOrderHistoryDTO> findPurchaseOrderHistoryByApprovalId(@Param("id") Long approvalId);

    // 첨부 파일 조회
    List<ApprovalFileDTO> findApprovalFilesByApprovalId(@Param("id") Long approvalId);



    String findLatestApprovalCode(String codePrefix);

    // 결재선 조회
    List<ApprovalLinesDTO> getApprovalDetailLines(long approvalId);

    // 결재선 정보 상세
    List<ApprovalLineDTO> findApprovalDetailLine(@Param("approvalId") Long approvalId);


    List<ApprovalLinesDTO> getApprovalLineTemplates(long userId);

    List<ApprovalLinesDTO> getApprovalLineDetailTemplates(long userId, long templateId);

    // 목록조회 - 결재자 조회
    List<ApprovalListLineDTO> findApprovalListLine(@Param("approvalId") Long approvalId);

    List<ApprovalListDTO> getApprovalListCooperateMyCompletedAll(long userId);
}
